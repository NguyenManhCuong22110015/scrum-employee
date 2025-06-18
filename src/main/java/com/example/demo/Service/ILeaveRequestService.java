package com.example.demo.Service;

import java.util.UUID;

import com.example.demo.DTO.Request.LeaveRequestDTO;
import com.example.demo.Model.Leave_Request;


public interface ILeaveRequestService {

    Leave_Request getById(UUID id);

    Leave_Request create(LeaveRequestDTO requestDTO);

    Leave_Request update(LeaveRequestDTO leaveRequestDTO);

    void delete(UUID id);


    // List<Leave_Request> getLeaveRequestByEmail(String email);

    void approveRequest(UUID id);

    void rejectRequest(UUID id);


}
