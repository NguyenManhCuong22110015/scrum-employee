package com.example.demo.Controllers;


import com.example.demo.DTO.Request.LeaveRequestDTO;
import com.example.demo.Model.Leave_Request;
import com.example.demo.Service.ILeaveRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    public ResponseEntity<Leave_Request> createLeaveRequest(@Valid @RequestBody LeaveRequestDTO requestDTO){
        Leave_Request leaveRequest= leaveRequestService.create(requestDTO);
        return ResponseEntity.ok(leaveRequest);
    }

}
