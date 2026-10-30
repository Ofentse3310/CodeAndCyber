package com.ofentse.leavemanagement.controller;

import com.ofentse.leavemanagement.model.*;
import com.ofentse.leavemanagement.repository.*;
import com.ofentse.leavemanagement.service.LeaveException;
import com.ofentse.leavemanagement.service.LeaveService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class LeaveController {

    private final LeaveService leaveService;
    private final UserRepository userRepository;
    private final LeaveTypeRepository typeRepository;
    private final LeaveBalanceRepository balanceRepository;
    private final LeaveRequestRepository requestRepository;

    public LeaveController(LeaveService leaveService,
                           UserRepository userRepository,
                           LeaveTypeRepository typeRepository,
                           LeaveBalanceRepository balanceRepository,
                           LeaveRequestRepository requestRepository) {
        this.leaveService = leaveService;
        this.userRepository = userRepository;
        this.typeRepository = typeRepository;
        this.balanceRepository = balanceRepository;
        this.requestRepository = requestRepository;
    }

    private User currentUser(Principal principal) {
        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalStateException("Logged-in user not found"));
    }

    @GetMapping("/")
    public String dashboard(Principal principal, Model model) {
        User user = currentUser(principal);
        model.addAttribute("user", user);
        model.addAttribute("balances", balanceRepository.findByUser(user));
        model.addAttribute("requests", requestRepository.findByUser(user));
        model.addAttribute("leaveTypes", typeRepository.findAll());
        if (user.getRole() == Role.MANAGER) {
            model.addAttribute("pending",
                    requestRepository.findByUserManagerAndStatus(user, LeaveStatus.PENDING));
        }
        return "dashboard";
    }

    @PostMapping("/leave/request")
    public String submit(Principal principal,
                         @RequestParam Long leaveTypeId,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                         @RequestParam(required = false) String reason,
                         RedirectAttributes redirect) {
        try {
            leaveService.submitRequest(currentUser(principal), leaveTypeId, startDate, endDate, reason);
            redirect.addFlashAttribute("message", "Leave request submitted.");
        } catch (LeaveException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/leave/{id}/approve")
    public String approve(@PathVariable Long id,
                          @RequestParam(required = false) String comment,
                          Principal principal, RedirectAttributes redirect) {
        try {
            leaveService.approve(id, currentUser(principal), comment);
            redirect.addFlashAttribute("message", "Request approved.");
        } catch (LeaveException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/leave/{id}/reject")
    public String reject(@PathVariable Long id,
                         @RequestParam(required = false) String comment,
                         Principal principal, RedirectAttributes redirect) {
        try {
            leaveService.reject(id, currentUser(principal), comment);
            redirect.addFlashAttribute("message", "Request rejected.");
        } catch (LeaveException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }
}
