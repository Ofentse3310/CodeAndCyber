package com.ofentse.leavemanagement.service;

import com.ofentse.leavemanagement.model.*;
import com.ofentse.leavemanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveService {

    private final LeaveRequestRepository requestRepository;
    private final LeaveTypeRepository typeRepository;
    private final LeaveBalanceRepository balanceRepository;

    public LeaveService(LeaveRequestRepository requestRepository,
                        LeaveTypeRepository typeRepository,
                        LeaveBalanceRepository balanceRepository) {
        this.requestRepository = requestRepository;
        this.typeRepository = typeRepository;
        this.balanceRepository = balanceRepository;
    }

    @Transactional
    public LeaveRequest submitRequest(User user, Long leaveTypeId,
                                      LocalDate start, LocalDate end, String reason) {
        if (start == null || end == null) {
            throw new LeaveException("Start and end dates are required.");
        }
        if (end.isBefore(start)) {
            throw new LeaveException("End date cannot be before start date.");
        }
        if (start.isBefore(LocalDate.now())) {
            throw new LeaveException("Leave cannot start in the past.");
        }

        LeaveType type = typeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new LeaveException("Leave type not found."));

        int days = countWorkingDays(start, end);
        if (days == 0) {
            throw new LeaveException("The selected dates contain no working days.");
        }

        checkOverlap(user, start, end);

        LeaveBalance balance = balanceRepository.findByUserAndLeaveType(user, type)
                .orElseThrow(() -> new LeaveException("No balance set up for this leave type."));
        if (days > balance.getDaysRemaining()) {
            throw new LeaveException("Not enough leave. You requested " + days
                    + " day(s) but have " + balance.getDaysRemaining() + ".");
        }

        LeaveRequest request = new LeaveRequest();
        request.setUser(user);
        request.setLeaveType(type);
        request.setStartDate(start);
        request.setEndDate(end);
        request.setReason(reason);
        return requestRepository.save(request);
    }

    @Transactional
    public LeaveRequest approve(Long requestId, User manager, String comment) {
        LeaveRequest request = loadPendingRequest(requestId, manager);

        int days = countWorkingDays(request.getStartDate(), request.getEndDate());
        LeaveBalance balance = balanceRepository
                .findByUserAndLeaveType(request.getUser(), request.getLeaveType())
                .orElseThrow(() -> new LeaveException("No balance set up for this leave type."));
        if (days > balance.getDaysRemaining()) {
            throw new LeaveException("Employee no longer has enough leave to approve this.");
        }

        balance.setDaysRemaining(balance.getDaysRemaining() - days);
        balanceRepository.save(balance);

        request.setStatus(LeaveStatus.APPROVED);
        request.setManagerComment(comment);
        return requestRepository.save(request);
    }

    @Transactional
    public LeaveRequest reject(Long requestId, User manager, String comment) {
        LeaveRequest request = loadPendingRequest(requestId, manager);
        request.setStatus(LeaveStatus.REJECTED);
        request.setManagerComment(comment);
        return requestRepository.save(request);
    }

    private LeaveRequest loadPendingRequest(Long requestId, User manager) {
        LeaveRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new LeaveException("Request not found."));
        User employeeManager = request.getUser().getManager();
        if (employeeManager == null || !employeeManager.getId().equals(manager.getId())) {
            throw new LeaveException("Only the employee's manager can decide this request.");
        }
        if (request.getStatus() != LeaveStatus.PENDING) {
            throw new LeaveException("This request has already been decided.");
        }
        return request;
    }

    private void checkOverlap(User user, LocalDate start, LocalDate end) {
        List<LeaveRequest> existing = requestRepository.findByUser(user);
        for (LeaveRequest r : existing) {
            if (r.getStatus() == LeaveStatus.REJECTED) continue;
            boolean overlaps = !start.isAfter(r.getEndDate()) && !end.isBefore(r.getStartDate());
            if (overlaps) {
                throw new LeaveException("These dates overlap with an existing request.");
            }
        }
    }

    private int countWorkingDays(LocalDate start, LocalDate end) {
        int count = 0;
        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            DayOfWeek day = d.getDayOfWeek();
            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) count++;
        }
        return count;
    }
}
