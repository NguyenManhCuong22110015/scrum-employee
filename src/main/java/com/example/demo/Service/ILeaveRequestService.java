package com.example.demo.Service;

import com.example.demo.DTO.Request.LeaveRequestDTO;
import com.example.demo.Model.Leave_Request;

import java.util.UUID;

public interface ILeaveRequestService {

    Leave_Request getById(UUID id);

    Leave_Request create(LeaveRequestDTO requestDTO);

    Leave_Request update(LeaveRequestDTO leaveRequestDTO);

    void delete(UUID id);

    void approveRequest();

}
