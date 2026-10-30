package com.ofentse.leavemanagement.config;

import com.ofentse.leavemanagement.model.*;
import com.ofentse.leavemanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final LeaveTypeRepository leaveTypeRepository;
    private final UserRepository userRepository;
    private final LeaveBalanceRepository balanceRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(LeaveTypeRepository leaveTypeRepository,
                      UserRepository userRepository,
                      LeaveBalanceRepository balanceRepository,
                      PasswordEncoder passwordEncoder) {
        this.leaveTypeRepository = leaveTypeRepository;
        this.userRepository = userRepository;
        this.balanceRepository = balanceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (leaveTypeRepository.count() == 0) {
            leaveTypeRepository.save(createType("Annual", 15));
            leaveTypeRepository.save(createType("Sick", 10));
            leaveTypeRepository.save(createType("Family Responsibility", 3));
        }

        if (userRepository.count() == 0) {
            User manager = createUser("Demo Manager", "manager@demo.test", Role.MANAGER, null);
            User employee = createUser("Demo Employee", "employee@demo.test", Role.EMPLOYEE, manager);

            for (LeaveType type : leaveTypeRepository.findAll()) {
                balanceRepository.save(new LeaveBalance(manager, type, type.getDaysPerYear()));
                balanceRepository.save(new LeaveBalance(employee, type, type.getDaysPerYear()));
            }
        }

        System.out.println("Leave types: " + leaveTypeRepository.count()
                + ", users: " + userRepository.count());
    }

    private LeaveType createType(String name, int days) {
        LeaveType type = new LeaveType();
        type.setName(name);
        type.setDaysPerYear(days);
        return type;
    }

    private User createUser(String name, String email, Role role, User manager) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode("Demo#12345"));
        user.setRole(role);
        user.setManager(manager);
        return userRepository.save(user);
    }
}
