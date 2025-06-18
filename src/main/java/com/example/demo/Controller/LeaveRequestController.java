package com.example.demo.Controller;


import java.util.UUID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.Request.LeaveRequestDTO;
import com.example.demo.Model.Leave_Request;
import com.example.demo.Service.IEmployeeService;
import com.example.demo.Service.ILeaveRequestService;



@RestController
@RequestMapping("/api/v1/leave-request")
@RequiredArgsConstructor
public class LeaveRequestController {

    private final ILeaveRequestService leaveRequestService;

    


    private final IEmployeeService employeeService;




    @GetMapping("/{id}")
    public ResponseEntity<Leave_Request> getLeaveRequestById(@PathVariable UUID id){
        Leave_Request leaveRequest = leaveRequestService.getById(id);
        return ResponseEntity.ok(leaveRequest);
    }


    
    // @GetMapping("/all/{email}")
    // public ResponseEntity<List<Leave_Request>> getAllEmployees(@PathVariable String email) {
    //     List<Leave_Request> leaveRequest = leaveRequestService.getLeaveRequestByEmail(email);
    //     return ResponseEntity.ok(leaveRequest);
    // }




    @PostMapping("/create")
    public ResponseEntity<Leave_Request> createLeaveRequest(@Valid @RequestBody LeaveRequestDTO requestDTO){
        Leave_Request leaveRequest= leaveRequestService.create(requestDTO);
        return ResponseEntity.ok(leaveRequest);
    }

    @DeleteMapping("/del/{id}")
    public String deleteLeaveRequest(@PathVariable UUID id){
        leaveRequestService.delete(id);
        return "Deleted Successfully!";
    }


    @PostMapping("/approve")
    public ResponseEntity<?> approveRequest(@RequestParam String emailRequester, @RequestParam UUID id) {
        if (!employeeService.isManager(emailRequester)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only managers can approve requests.");
        }

        leaveRequestService.approveRequest(id);
        return ResponseEntity.ok("Leave request approved.");
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectRequest(@RequestParam String emailRequester, @RequestParam UUID id) {
        if (!employeeService.isManager(emailRequester)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only managers can reject requests.");
        }

        leaveRequestService.rejectRequest(id);
        return ResponseEntity.ok("Leave request rejected.");
    }



}
