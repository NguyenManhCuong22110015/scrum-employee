package com.example.demo.Controllers;

import com.example.demo.DTO.Request.EmployeeDTO;
import com.example.demo.Model.Employee;
import com.example.demo.Service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final IEmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        Employee employee = employeeService.getById(email);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.create(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String email, 
                                                  @Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee updatedEmployee = employeeService.update(email, employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String email) {
        employeeService.delete(email);
        return ResponseEntity.ok("Employee deleted successfully!");
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable String department) {
        List<Employee> employees = employeeService.getByDepartment(department);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/manager/{managerEmail}")
    public ResponseEntity<List<Employee>> getEmployeesByManager(@PathVariable String managerEmail) {
        List<Employee> employees = employeeService.getByManagerEmail(managerEmail);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> checkEmployeeExists(@PathVariable String email) {
        boolean exists = employeeService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}
