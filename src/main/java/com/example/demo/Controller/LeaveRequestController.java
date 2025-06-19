package com.example.demo.Controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.Request.LeaveRequestDTO;
import com.example.demo.Enum.Leave_Status_Enum;
import com.example.demo.Model.Leave_Request;
import com.example.demo.Service.ILeaveRequestService;

@RestController
@RequestMapping("/api/v1/leave-request")
@RequiredArgsConstructor
public class LeaveRequestController {

    private final ILeaveRequestService leaveRequestService;

    @GetMapping
    public ResponseEntity<List<Leave_Request>> getAllLeaveRequests() {
        List<Leave_Request> requests = leaveRequestService.getAll();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leave_Request> getLeaveRequestById(@PathVariable UUID id) {
        Leave_Request leaveRequest = leaveRequestService.getById(id);
        return ResponseEntity.ok(leaveRequest);
    }

    @GetMapping("/employee/{email}")
    public ResponseEntity<List<Leave_Request>> getLeaveRequestsByEmployee(@PathVariable String email) {
        List<Leave_Request> requests = leaveRequestService.getLeaveRequestsByEmployee(email);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Leave_Request>> getLeaveRequestsByStatus(@PathVariable Leave_Status_Enum status) {
        List<Leave_Request> requests = leaveRequestService.getLeaveRequestsByStatus(status);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/employee/{email}/status/{status}")
    public ResponseEntity<List<Leave_Request>> getLeaveRequestsByEmployeeAndStatus(
            @PathVariable String email, 
            @PathVariable Leave_Status_Enum status) {
        List<Leave_Request> requests = leaveRequestService.getLeaveRequestsByEmployeeAndStatus(email, status);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/create")
    public ResponseEntity<Leave_Request> createLeaveRequest(@Valid @RequestBody LeaveRequestDTO requestDTO) {
        Leave_Request leaveRequest = leaveRequestService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(leaveRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Leave_Request> updateLeaveRequest(
            @PathVariable UUID id, 
            @Valid @RequestBody LeaveRequestDTO requestDTO) {
        Leave_Request updatedRequest = leaveRequestService.update(id, requestDTO);
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLeaveRequest(@PathVariable UUID id) {
        leaveRequestService.delete(id);
        return ResponseEntity.ok("Leave request deleted successfully!");
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveLeaveRequest(
            @PathVariable UUID id, 
            @RequestParam String approverEmail) {
        leaveRequestService.approveRequest(id, approverEmail);
        return ResponseEntity.ok("Leave request approved successfully!");
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<String> rejectLeaveRequest(
            @PathVariable UUID id, 
            @RequestParam String approverEmail) {
        leaveRequestService.rejectRequest(id, approverEmail);
        return ResponseEntity.ok("Leave request rejected successfully!");
    }
}
