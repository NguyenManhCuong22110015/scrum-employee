package com.example.demo.Model;

import com.example.demo.Enum.Employee_Role_Enum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.StringReader;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @Column(name = "email", nullable = false, unique = true)
    private String email;


    @Column(name = "full_name", nullable = false)
    private String full_name;

    @Column(name = "department", nullable = false, length = 100)
    private String department;

    @Enumerated(EnumType.STRING)
    private Employee_Role_Enum role = Employee_Role_Enum.EMPLOYEE;

    @Column(name = "manager_email", nullable = false, length = 100)
    private String manager_email;

    @Column(name = "leave_balance")
    private Integer leave_balance = 12;


}
