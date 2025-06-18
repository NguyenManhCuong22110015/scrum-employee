package com.example.demo.Controllers;


import java.util.List;

import com.example.demo.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.Services.EmployeeRepository;


import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/employees")
public class EmployeeController {


    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/find/{email}")
    public Employee getEmployeeById(@PathVariable String email) {
        return employeeRepository.findById(email).orElse(null);
    }

    @PutMapping("/update/{email}")
    public Employee updateEmployee(@PathVariable String email, @RequestBody Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(email).orElse(null);
        if (employee != null) {
            employee.setFull_name(updatedEmployee.getFull_name());
            employee.setDepartment(updatedEmployee.getDepartment());
            employee.setRole(updatedEmployee.getRole());
            employee.setLeave_balance(updatedEmployee.getLeave_balance());
            employee.setManager_email(updatedEmployee.getManager_email());
            return employeeRepository.save(employee);
        } else {
            return null;
        }
    }

    @PostMapping("/add")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @DeleteMapping("/delete/{email}")
    public void deleteEmployee(@PathVariable String email) {
        employeeRepository.deleteById(email);
    }



    private Map<String, Employee> employeeMap = new HashMap<>();


    @GetMapping("/{email}")
    public Employee getEmployee(@PathVariable String email) {
        return employeeMap.get(email);
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        employeeMap.put(employee.getEmail(), employee);
        return employee;
    }





}
