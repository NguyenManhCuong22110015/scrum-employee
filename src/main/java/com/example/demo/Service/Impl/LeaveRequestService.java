package com.example.demo.Service.Impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.Request.LeaveRequestDTO;
import com.example.demo.Enum.Leave_Status_Enum;
import com.example.demo.Exception.InvalidDateRangeException;
import com.example.demo.Exception.InsufficientLeaveBalanceException;
import com.example.demo.Exception.LeaveRequestConflictException;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Exception.UnauthorizedOperationException;
import com.example.demo.Model.Employee;
import com.example.demo.Model.Leave_Request;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Repository.LeaveRequestRepository;
import com.example.demo.Service.ILeaveBalanceService;
import com.example.demo.Service.ILeaveRequestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LeaveRequestService implements ILeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final ILeaveBalanceService leaveBalanceService;

    @Override
    public Leave_Request getById(UUID id) {
        try {
            Leave_Request leaveRequest = leaveRequestRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Leave Request not found with ID: " + id));
            log.info("Retrieved leave request with ID: {}", id);
            return leaveRequest;
        } catch (Exception e) {
            log.error("Error retrieving leave request with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Leave_Request> getAll() {
        try {
            List<Leave_Request> requests = leaveRequestRepository.findAll();
            log.info("Retrieved {} leave requests", requests.size());
            return requests;
        } catch (Exception e) {
            log.error("Error retrieving all leave requests: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Leave_Request create(LeaveRequestDTO requestDTO) {
        try {
            // Validate dates
            validateDateRange(requestDTO.getStartDate(), requestDTO.getEndDate());
              // Check if employee exists
            employeeRepository.findById(requestDTO.getEmployeeEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + requestDTO.getEmployeeEmail()));
            
            // Check for conflicting requests
            if (hasConflictingRequests(requestDTO.getEmployeeEmail(), requestDTO.getStartDate(), requestDTO.getEndDate())) {
                throw new LeaveRequestConflictException("You already have a leave request for this period");
            }
            
            // Calculate actual leave days needed
            long actualDays = leaveBalanceService.calculateActualLeaveDays(requestDTO.getStartDate(), requestDTO.getEndDate());
            
            // Check leave balance
            if (!leaveBalanceService.hasEnoughLeaveBalance(requestDTO.getEmployeeEmail(), actualDays)) {
                throw new InsufficientLeaveBalanceException(
                    String.format("Insufficient leave balance. Required: %d days, Available: %d days", 
                        actualDays, leaveBalanceService.getCurrentLeaveBalance(requestDTO.getEmployeeEmail()))
                );
            }

            Leave_Request leaveRequest = new Leave_Request();
            leaveRequest.setEmployeeEmail(requestDTO.getEmployeeEmail());
            leaveRequest.setLeaveType(requestDTO.getLeaveType());
            leaveRequest.setStartDate(requestDTO.getStartDate());
            leaveRequest.setEndDate(requestDTO.getEndDate());
            leaveRequest.setReason(requestDTO.getReason());
            leaveRequest.setLeaveStatus(Leave_Status_Enum.PENDING);
            leaveRequest.setApprovedAt(null);
            leaveRequest.setCreateAt(new Timestamp(System.currentTimeMillis()));

            Leave_Request savedRequest = leaveRequestRepository.save(leaveRequest);
            log.info("Created leave request for employee {}, {} days from {} to {}", 
                    requestDTO.getEmployeeEmail(), actualDays, requestDTO.getStartDate(), requestDTO.getEndDate());
            
            return savedRequest;
        } catch (Exception e) {
            log.error("Error creating leave request: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Leave_Request update(UUID id, LeaveRequestDTO leaveRequestDTO) {
        try {
            Leave_Request existingRequest = getById(id);
            
            // Only allow update if status is PENDING
            if (existingRequest.getLeaveStatus() != Leave_Status_Enum.PENDING) {
                throw new UnauthorizedOperationException("Cannot update non-pending leave request");
            }
            
            // Validate new dates
            validateDateRange(leaveRequestDTO.getStartDate(), leaveRequestDTO.getEndDate());
            
            // Check for conflicts (excluding current request)
            List<Leave_Request> conflicts = leaveRequestRepository.findConflictingRequests(
                existingRequest.getEmployeeEmail(), 
                leaveRequestDTO.getStartDate(), 
                leaveRequestDTO.getEndDate()
            );
            conflicts.removeIf(req -> req.getId().equals(id)); // Exclude current request
            
            if (!conflicts.isEmpty()) {
                throw new LeaveRequestConflictException("Updated dates conflict with existing leave requests");
            }
            
            existingRequest.setLeaveType(leaveRequestDTO.getLeaveType());
            existingRequest.setStartDate(leaveRequestDTO.getStartDate());
            existingRequest.setEndDate(leaveRequestDTO.getEndDate());
            existingRequest.setReason(leaveRequestDTO.getReason());
            
            Leave_Request updatedRequest = leaveRequestRepository.save(existingRequest);
            log.info("Updated leave request with ID: {}", id);
            return updatedRequest;
        } catch (Exception e) {
            log.error("Error updating leave request with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            Leave_Request request = getById(id);
            
            // Only allow deletion if status is PENDING
            if (request.getLeaveStatus() != Leave_Status_Enum.PENDING) {
                throw new UnauthorizedOperationException("Cannot delete non-pending leave request");
            }
            
            leaveRequestRepository.deleteById(id);
            log.info("Deleted leave request with ID: {}", id);
        } catch (Exception e) {
            log.error("Error deleting leave request with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Leave_Request> getLeaveRequestsByEmployee(String employeeEmail) {
        return leaveRequestRepository.findByEmployeeEmail(employeeEmail);
    }

    @Override
    public List<Leave_Request> getLeaveRequestsByStatus(Leave_Status_Enum status) {
        return leaveRequestRepository.findByLeaveStatus(status);
    }

    @Override
    public List<Leave_Request> getLeaveRequestsByEmployeeAndStatus(String employeeEmail, Leave_Status_Enum status) {
        return leaveRequestRepository.findByEmployeeEmailAndLeaveStatus(employeeEmail, status);
    }

    @Override
    public void approveRequest(UUID id, String approverEmail) {
        try {
            Leave_Request leaveRequest = getById(id);
            
            if (leaveRequest.getLeaveStatus() != Leave_Status_Enum.PENDING) {
                throw new UnauthorizedOperationException("Can only approve pending requests");
            }
            
            // Verify approver is the manager
            Employee employee = employeeRepository.findById(leaveRequest.getEmployeeEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
            
            if (!employee.getManagerEmail().equals(approverEmail)) {
                throw new UnauthorizedOperationException("Only the employee's manager can approve this request");
            }
            
            // Calculate and deduct leave balance
            long actualDays = leaveBalanceService.calculateActualLeaveDays(
                leaveRequest.getStartDate(), leaveRequest.getEndDate());
            leaveBalanceService.deductLeaveBalance(leaveRequest.getEmployeeEmail(), actualDays);
            
            leaveRequest.setLeaveStatus(Leave_Status_Enum.APPROVED);
            leaveRequest.setApprovedAt(new Timestamp(System.currentTimeMillis()));
            leaveRequestRepository.save(leaveRequest);
            
            log.info("Approved leave request {} by manager {}, deducted {} days", 
                    id, approverEmail, actualDays);
        } catch (Exception e) {
            log.error("Error approving leave request {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void rejectRequest(UUID id, String approverEmail) {
        try {
            Leave_Request leaveRequest = getById(id);
            
            if (leaveRequest.getLeaveStatus() != Leave_Status_Enum.PENDING) {
                throw new UnauthorizedOperationException("Can only reject pending requests");
            }
            
            // Verify approver is the manager
            Employee employee = employeeRepository.findById(leaveRequest.getEmployeeEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
            
            if (!employee.getManagerEmail().equals(approverEmail)) {
                throw new UnauthorizedOperationException("Only the employee's manager can reject this request");
            }
            
            leaveRequest.setLeaveStatus(Leave_Status_Enum.REJECTED);
            leaveRequest.setApprovedAt(new Timestamp(System.currentTimeMillis()));
            leaveRequestRepository.save(leaveRequest);
            
            log.info("Rejected leave request {} by manager {}", id, approverEmail);
        } catch (Exception e) {
            log.error("Error rejecting leave request {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Leave_Request> getLeaveRequestsForManager(String managerEmail) {
        return leaveRequestRepository.findByManagerEmail(managerEmail);
    }

    @Override
    public List<Leave_Request> getPendingRequestsForManager(String managerEmail) {
        return leaveRequestRepository.findByManagerEmailAndStatus(managerEmail, Leave_Status_Enum.PENDING);
    }

    @Override
    public boolean hasConflictingRequests(String employeeEmail, LocalDate startDate, LocalDate endDate) {
        List<Leave_Request> conflicts = leaveRequestRepository.findConflictingRequests(
            employeeEmail, startDate, endDate);
        return !conflicts.isEmpty();
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new InvalidDateRangeException("Start date and end date cannot be null");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new InvalidDateRangeException("Start date cannot be after end date");
        }
        
        if (startDate.isBefore(LocalDate.now())) {
            throw new InvalidDateRangeException("Cannot create leave request for past dates");
        }
        
        // Optional: Require advance notice (e.g., 3 days)
        if (startDate.isBefore(LocalDate.now().plusDays(3))) {
            throw new InvalidDateRangeException("Leave request must be submitted at least 3 days in advance");
        }
    }
}
