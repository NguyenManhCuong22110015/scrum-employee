package com.example.demo.Service;

import com.example.demo.DTO.Request.EmployeeDTO;
import com.example.demo.Model.Employee;

import java.util.List;
import java.util.UUID;

public interface IEmployeeService {

    Employee getById(String email);
    
    List<Employee> getAll();
    
    Employee create(EmployeeDTO employeeDTO);
    
    Employee update(String email, EmployeeDTO employeeDTO);
    
    void delete(String email);
    
    List<Employee> getByDepartment(String department);
    
    List<Employee> getByManagerEmail(String managerEmail);
    
    boolean existsByEmail(String email);

    boolean isManager(String emailRequester);

}
