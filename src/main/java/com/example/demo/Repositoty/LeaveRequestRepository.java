package com.example.demo.Repositoty;

import com.example.demo.Model.Leave_Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeaveRequestRepository extends JpaRepository<Leave_Request, UUID> {
}
