package com.example.demo.DTO.Response;

import com.example.demo.Enum.Employee_Role_Enum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private String email;
    private String fullName;
    private String department;
    private Employee_Role_Enum role;
    private String managerEmail;
    private Integer leaveBalance;
}
