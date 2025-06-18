package com.example.demo.Controllers;

import com.example.demo.DTO.Response.EmployeeResponse;
import com.example.demo.Model.Employee;
import com.example.demo.Service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IEmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String email) {
        Employee employee = employeeService.getById(email);
        if (employee == null) {
            return ResponseEntity.status(404).body("Employee not found");
        }
        EmployeeResponse response = new EmployeeResponse(
                employee.getEmail(),
                employee.getFullName(),
                employee.getDepartment(),
                employee.getRole(),
                employee.getManagerEmail(),
                employee.getLeaveBalance()
        );
        return ResponseEntity.ok(response);
    }
}
