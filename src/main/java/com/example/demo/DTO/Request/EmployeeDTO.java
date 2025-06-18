package com.example.demo.DTO.Request;

import com.example.demo.Enum.Employee_Role_Enum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Department is required")
    private String department;

    @NotNull(message = "Role is required")
    private Employee_Role_Enum role;

    @Email(message = "Manager email should be valid")
    @NotBlank(message = "Manager email is required")
    private String managerEmail;

    private Integer leaveBalance;
}
