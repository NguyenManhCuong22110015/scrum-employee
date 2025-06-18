package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.demo.Model.Leave_Request;

@Repository
public interface LeaveRequestRepository extends JpaRepository<Leave_Request, java.util.UUID> {
    List<Leave_Request> findByEmployeeEmail(String employeeEmail);
}

