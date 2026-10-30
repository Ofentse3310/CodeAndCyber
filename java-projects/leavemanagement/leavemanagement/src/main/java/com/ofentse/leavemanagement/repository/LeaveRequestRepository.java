package com.ofentse.leavemanagement.repository;

import com.ofentse.leavemanagement.model.LeaveRequest;
import com.ofentse.leavemanagement.model.LeaveStatus;
import com.ofentse.leavemanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByUser(User user);
    List<LeaveRequest> findByStatus(LeaveStatus status);
    List<LeaveRequest> findByUserManagerAndStatus(User manager, LeaveStatus status);
}
