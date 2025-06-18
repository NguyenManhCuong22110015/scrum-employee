package com.example.demo.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Employees;
import com.example.demo.Services.Employee;

import com.example.demo.Model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/employees")
public class EmployeeController {


    @Autowired
    private Employee employeeRepository;

    @GetMapping
    public List<Employees> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/find/{email}")
    public Employees getEmployeeById(@PathVariable String email) {
        return employeeRepository.findById(email).orElse(null);
    }

    @PutMapping("/update/{email}")
    public Employees updateEmployee(@PathVariable String email, @RequestBody Employees updatedEmployee) {
        Employees employee = employeeRepository.findById(email).orElse(null);
        if (employee != null) {
            employee.setFullName(updatedEmployee.getFullName());
            employee.setDepartment(updatedEmployee.getDepartment());
            employee.setEmployeesRole(updatedEmployee.getEmployeesRole());
            employee.setLeaveBalance(updatedEmployee.getLeaveBalance());
            employee.setManagerEmail(updatedEmployee.getManagerEmail());
            return employeeRepository.save(employee);
        } else {
            return null;
        }
    }

    @PostMapping("/add")
    public Employees createEmployee(@RequestBody Employees employee) {
        return employeeRepository.save(employee);
    }

    @DeleteMapping("/delete/{email}")
    public void deleteEmployee(@PathVariable String email) {
        employeeRepository.deleteById(email);
    }



    private Map<String, Employee> employeeMap = new HashMap<>();

    @GetMapping
    public Collection<Employee> getAllEmployees() {
        return employeeMap.values();
    }

    @GetMapping("/{email}")
    public Employee getEmployee(@PathVariable String email) {
        return employeeMap.get(email);
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        employeeMap.put(employee.getEmail(), employee);
        return employee;
    }

    @PutMapping("/{email}")
    public Employee updateEmployee(@PathVariable String email, @RequestBody Employee updatedEmployee) {
        employeeMap.put(email, updatedEmployee);
        return updatedEmployee;
    }

    @DeleteMapping("/{email}")
    public String deleteEmployee(@PathVariable String email) {
        employeeMap.remove(email);
        return "Deleted: " + email;
    }

}
