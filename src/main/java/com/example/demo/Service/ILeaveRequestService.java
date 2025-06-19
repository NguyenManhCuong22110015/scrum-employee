package com.example.demo.Service;

import java.util.List;
import java.util.UUID;

import com.example.demo.DTO.Request.LeaveRequestDTO;
import com.example.demo.Enum.Leave_Status_Enum;
import com.example.demo.Model.Leave_Request;

public interface ILeaveRequestService {

    Leave_Request getById(UUID id);
    
    List<Leave_Request> getAll();

    Leave_Request create(LeaveRequestDTO requestDTO);

    Leave_Request update(UUID id, LeaveRequestDTO leaveRequestDTO);

    void delete(UUID id);

    List<Leave_Request> getLeaveRequestsByEmployee(String employeeEmail);
    
    List<Leave_Request> getLeaveRequestsByStatus(Leave_Status_Enum status);
    
    List<Leave_Request> getLeaveRequestsByEmployeeAndStatus(String employeeEmail, Leave_Status_Enum status);

    void approveRequest(UUID id, String approverEmail);

    void rejectRequest(UUID id, String approverEmail);
    
    List<Leave_Request> getLeaveRequestsForManager(String managerEmail);
    
    List<Leave_Request> getPendingRequestsForManager(String managerEmail);
    
    boolean hasConflictingRequests(String employeeEmail, java.time.LocalDate startDate, java.time.LocalDate endDate);
}
