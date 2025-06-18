package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employees {

    @Id
    private String email;

    private String full_name;

    private String department;

    @Enumerated(EnumType.STRING)
    private EmployeeRole employees_role;

    private Integer leave_balance;

    private String manager_email;

    public Employees() {
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return full_name;
    }

    public void setFullName(String fullName) {
        this.full_name = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public EmployeeRole getEmployeesRole() {
        return employees_role;
    }

    public void setEmployeesRole(EmployeeRole employeesRole) {
        this.employees_role = employeesRole;
    }

    public Integer getLeaveBalance() {
        return leave_balance;
    }

    public void setLeaveBalance(Integer leaveBalance) {
        this.leave_balance = leaveBalance;
    }

    public String getManagerEmail() {
        return manager_email;
    }

    public void setManagerEmail(String managerEmail) {
        this.manager_email = managerEmail;
    }
}
