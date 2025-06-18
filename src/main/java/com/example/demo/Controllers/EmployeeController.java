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


}
