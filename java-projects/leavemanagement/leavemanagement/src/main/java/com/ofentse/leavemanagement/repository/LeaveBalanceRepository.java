package com.ofentse.leavemanagement.repository;

import com.ofentse.leavemanagement.model.LeaveBalance;
import com.ofentse.leavemanagement.model.LeaveType;
import com.ofentse.leavemanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
    List<LeaveBalance> findByUser(User user);
    Optional<LeaveBalance> findByUserAndLeaveType(User user, LeaveType leaveType);
}
