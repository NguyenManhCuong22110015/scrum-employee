package com.example.demo.DTO.Request;

import com.example.demo.Enum.Leave_Status_Enum;
import com.example.demo.Enum.Leave_Type_Enum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestDTO {
    private String employeeEmail;
    private Leave_Type_Enum leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}
