package com.example.demo.Repository;

import com.example.demo.Model.Leave_Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LeaveRequestRepository extends JpaRepository<Leave_Request, UUID> {
    List<Leave_Request> findByEmployeeEmail(String employeeEmail);
}
