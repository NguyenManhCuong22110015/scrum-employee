package com.example.demo.Service;

import com.example.demo.DTO.Request.EmployeeDTO;
import com.example.demo.Model.Employee;

import java.util.List;

public interface IEmployeeService {

    Employee getById(String email);
    
    List<Employee> getAll();
    
    Employee create(EmployeeDTO employeeDTO);
    
    Employee update(String email, EmployeeDTO employeeDTO);
    
    void delete(String email);
    
    List<Employee> getByDepartment(String department);
    
    List<Employee> getByManagerEmail(String managerEmail);
    
    boolean existsByEmail(String email);
}
