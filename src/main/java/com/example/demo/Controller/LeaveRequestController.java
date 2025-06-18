package com.example.demo.Controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.Request.LeaveRequestDTO;
import com.example.demo.Model.Leave_Request;
import com.example.demo.Service.ILeaveRequestService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/v1/leave-request")
@RequiredArgsConstructor
public class LeaveRequestController {

    private final ILeaveRequestService leaveRequestService;
    

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

}
