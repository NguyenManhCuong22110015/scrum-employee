package com.example.demo.Controllers;

import com.example.demo.Model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

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
