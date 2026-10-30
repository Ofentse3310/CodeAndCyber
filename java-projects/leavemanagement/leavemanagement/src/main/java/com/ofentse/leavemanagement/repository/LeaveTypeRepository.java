package com.ofentse.leavemanagement.repository;

import com.ofentse.leavemanagement.model.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
}
