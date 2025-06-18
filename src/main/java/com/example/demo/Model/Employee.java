package com.example.demo.Model;

import com.example.demo.Enum.Employee_Role_Enum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    
    @Id
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "fullName", nullable = false, length = 255)
    private String fullName;

    @Column(name = "department", nullable = false, length = 100)
    private String department;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Employee_Role_Enum role = Employee_Role_Enum.EMPLOYEE;

    @Column(name = "managerEmail", nullable = false, length = 255)
    private String managerEmail;

    @Column(name = "leaveBalance", nullable = false)
    private Integer leaveBalance = 12;
}
