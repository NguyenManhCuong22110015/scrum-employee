package com.example.demo.Controllers;


import com.example.demo.DTO.Request.LeaveRequestDTO;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.Leave_Request;
import com.example.demo.Service.IEmployeeService;
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
    private final IEmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<Leave_Request> getLeaveRequestById(@PathVariable UUID id){
        Leave_Request leaveRequest = leaveRequestService.getById(id);
        return ResponseEntity.ok(leaveRequest);
    }

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
    public String approveRequest(@RequestParam  String emailRequester, @RequestParam UUID id){
        if(employeeService.checkIsAdmin(emailRequester)){
            throw  new ResourceNotFoundException("No");
        }

        return "Yes";
    }

}
