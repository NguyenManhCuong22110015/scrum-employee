package com.example.demo.DTO.Request;

import com.example.demo.Enum.Leave_Status_Enum;
import com.example.demo.Enum.Leave_Type_Enum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequestDTO {
    @NotNull
    private String employeeEmail;

    @NotNull
    private Leave_Type_Enum leaveType;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotBlank
    private String reason;
}
