package com.example.demo.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Enum.Leave_Status_Enum;
import com.example.demo.Model.Leave_Request;

@Repository
public interface LeaveRequestRepository extends JpaRepository<Leave_Request, UUID> {
    
    List<Leave_Request> findByEmployeeEmail(String employeeEmail);
    
    List<Leave_Request> findByLeaveStatus(Leave_Status_Enum status);
    
    List<Leave_Request> findByEmployeeEmailAndLeaveStatus(String employeeEmail, Leave_Status_Enum status);
    
    @Query("SELECT lr FROM Leave_Request lr WHERE lr.employeeEmail = :employeeEmail " +
           "AND ((lr.startDate BETWEEN :startDate AND :endDate) " +
           "OR (lr.endDate BETWEEN :startDate AND :endDate) " +
           "OR (lr.startDate <= :startDate AND lr.endDate >= :endDate)) " +
           "AND lr.leaveStatus IN ('PENDING', 'APPROVED')")
    List<Leave_Request> findConflictingRequests(@Param("employeeEmail") String employeeEmail,
                                              @Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);
    
    @Query("SELECT lr FROM Leave_Request lr " +
           "JOIN Employee e ON lr.employeeEmail = e.email " +
           "WHERE e.managerEmail = :managerEmail")
    List<Leave_Request> findByManagerEmail(@Param("managerEmail") String managerEmail);
    
    @Query("SELECT lr FROM Leave_Request lr " +
           "JOIN Employee e ON lr.employeeEmail = e.email " +
           "WHERE e.managerEmail = :managerEmail AND lr.leaveStatus = :status")
    List<Leave_Request> findByManagerEmailAndStatus(@Param("managerEmail") String managerEmail,
                                                   @Param("status") Leave_Status_Enum status);
}

