package com.example.demo.Controller;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Leave_Request;
import com.example.demo.Service.IEmployeeService;
import com.example.demo.Service.ILeaveRequestService;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ILeaveRequestService leaveRequestService;
    private final IEmployeeService employeeService;

    @GetMapping("/{managerEmail}/team")
    public ResponseEntity<List<Employee>> getTeamMembers(@PathVariable String managerEmail) {
        List<Employee> teamMembers = employeeService.getByManagerEmail(managerEmail);
        return ResponseEntity.ok(teamMembers);
    }

    @GetMapping("/{managerEmail}/leave-requests")
    public ResponseEntity<List<Leave_Request>> getTeamLeaveRequests(@PathVariable String managerEmail) {
        List<Leave_Request> requests = leaveRequestService.getLeaveRequestsForManager(managerEmail);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{managerEmail}/leave-requests/pending")
    public ResponseEntity<List<Leave_Request>> getPendingLeaveRequests(@PathVariable String managerEmail) {
        List<Leave_Request> pendingRequests = leaveRequestService.getPendingRequestsForManager(managerEmail);
        return ResponseEntity.ok(pendingRequests);
    }

    @PutMapping("/approve/{requestId}")
    public ResponseEntity<String> approveLeaveRequest(
            @PathVariable UUID requestId,
            @RequestParam String managerEmail) {
        leaveRequestService.approveRequest(requestId, managerEmail);
        return ResponseEntity.ok("Leave request approved successfully!");
    }

    @PutMapping("/reject/{requestId}")
    public ResponseEntity<String> rejectLeaveRequest(
            @PathVariable UUID requestId,
            @RequestParam String managerEmail) {
        leaveRequestService.rejectRequest(requestId, managerEmail);
        return ResponseEntity.ok("Leave request rejected successfully!");
    }
}
