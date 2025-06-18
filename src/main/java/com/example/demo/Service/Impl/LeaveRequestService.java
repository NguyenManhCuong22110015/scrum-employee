package com.example.demo.Service.Impl;

import com.example.demo.DTO.Request.LeaveRequestDTO;
import com.example.demo.Enum.Leave_Status_Enum;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.Leave_Request;
import com.example.demo.Repository.LeaveRequestRepository;
import com.example.demo.Service.ILeaveRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LeaveRequestService implements ILeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;

    @Override
    public Leave_Request getById(UUID id) {
        try {
            Leave_Request leaveRequest = leaveRequestRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Leave Request not found"));
            return leaveRequest;
        }
        catch (Exception e){
            log.error("Error updating product with id {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
    @Override
    public Leave_Request create(LeaveRequestDTO requestDTO) {
        try {
            Leave_Request leaveRequest = new Leave_Request();

            leaveRequest.setEmployee_email(requestDTO.getEmployeeEmail());
            leaveRequest.setLeave_type(requestDTO.getLeaveType());
            leaveRequest.setStart_date(requestDTO.getStartDate());
            leaveRequest.setEnd_date(requestDTO.getEndDate());
            leaveRequest.setReason(requestDTO.getReason());
            leaveRequest.setLeave_status(Leave_Status_Enum.PENDING); // default
            leaveRequest.setApproved_at(null); // chưa duyệt
            leaveRequest.setCreate_at(new Timestamp(System.currentTimeMillis()));
            Leave_Request requestSaved = leaveRequestRepository.save(leaveRequest);
            return requestSaved;
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public Leave_Request update(LeaveRequestDTO leaveRequestDTO) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public void approveRequest() {

    }
}
