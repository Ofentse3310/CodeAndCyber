package com.ofentse.leavemanagement;

import com.ofentse.leavemanagement.model.*;
import com.ofentse.leavemanagement.repository.*;
import com.ofentse.leavemanagement.service.LeaveException;
import com.ofentse.leavemanagement.service.LeaveService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LeaveServiceTest {

    @Autowired LeaveService leaveService;
    @Autowired UserRepository userRepository;
    @Autowired LeaveTypeRepository typeRepository;
    @Autowired LeaveBalanceRepository balanceRepository;

    private User makeUser(String name, String email, Role role, User manager) {
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPasswordHash("not-a-real-hash");
        u.setRole(role);
        u.setManager(manager);
        return userRepository.save(u);
    }

    private LocalDate nextMonday() {
        return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
    }

    @Test
    void employeeCanRequestAndManagerCanApprove() {
        User manager = makeUser("Boss", "boss@test.com", Role.MANAGER, null);
        User employee = makeUser("Emp", "emp@test.com", Role.EMPLOYEE, manager);
        LeaveType annual = typeRepository.findAll().get(0);
        balanceRepository.save(new LeaveBalance(employee, annual, 5));

        LeaveRequest request = leaveService.submitRequest(employee, annual.getId(),
                nextMonday(), nextMonday().plusDays(2), "Family event");
        assertEquals(LeaveStatus.PENDING, request.getStatus());

        leaveService.approve(request.getId(), manager, "Enjoy");
        LeaveBalance updated = balanceRepository.findByUserAndLeaveType(employee, annual).get();
        assertEquals(2, updated.getDaysRemaining());
    }

    @Test
    void rejectsEndBeforeStart() {
        User employee = makeUser("Emp", "emp2@test.com", Role.EMPLOYEE, null);
        LeaveType annual = typeRepository.findAll().get(0);
        balanceRepository.save(new LeaveBalance(employee, annual, 5));

        assertThrows(LeaveException.class, () ->
                leaveService.submitRequest(employee, annual.getId(),
                        nextMonday().plusDays(3), nextMonday(), "Bad dates"));
    }

    @Test
    void rejectsRequestOverBalance() {
        User employee = makeUser("Emp", "emp3@test.com", Role.EMPLOYEE, null);
        LeaveType annual = typeRepository.findAll().get(0);
        balanceRepository.save(new LeaveBalance(employee, annual, 1));

        assertThrows(LeaveException.class, () ->
                leaveService.submitRequest(employee, annual.getId(),
                        nextMonday(), nextMonday().plusDays(4), "Too long"));
    }
}