package com.example.demo.Service.Impl;

import java.util.List;

import com.example.demo.Enum.Employee_Role_Enum;
import com.example.demo.Exception.EmployeeAlreadyExists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.Request.EmployeeDTO;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.Employee;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Service.IEmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.UUID;



@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee getById(String email) {
        try {
            Employee employee = employeeRepository.findById(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email: " + email));
            log.info("Retrieved employee with email: {}", email);
            return employee;
        } catch (Exception e) {
            log.error("Error retrieving employee with email {}: {}", email, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Employee> getAll() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            log.info("Retrieved {} employees", employees.size());
            return employees;
        } catch (Exception e) {
            log.error("Error retrieving all employees: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Employee create(EmployeeDTO employeeDTO) {
        try {
            if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
                throw new EmployeeAlreadyExists("Employee already exists");
            }

            Employee employee = new Employee();
            employee.setEmail(employeeDTO.getEmail());
            employee.setFullName(employeeDTO.getFullName());
            employee.setDepartment(employeeDTO.getDepartment());
            employee.setRole(employeeDTO.getRole());
            employee.setManagerEmail(employeeDTO.getManagerEmail());
            Employee savedEmployee = employeeRepository.save(employee);

            log.info("Created new employee with email: {}", savedEmployee.getEmail());
            return savedEmployee;

        } catch (Exception e) {
            log.error("Error creating employee: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Employee update(String email, EmployeeDTO employeeDTO) {
        try {
            Employee existingEmployee = employeeRepository.findById(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email: " + email));

            existingEmployee.setFullName(employeeDTO.getFullName());
            existingEmployee.setDepartment(employeeDTO.getDepartment());
            existingEmployee.setRole(employeeDTO.getRole());
            existingEmployee.setManagerEmail(employeeDTO.getManagerEmail());
            if (employeeDTO.getLeaveBalance()  != null) {
                existingEmployee.setLeaveBalance(employeeDTO.getLeaveBalance());
            }

            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            log.info("Updated employee with email: {}", email);
            return updatedEmployee;
        } catch (Exception e) {
            log.error("Error updating employee with email {}: {}", email, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void delete(String email) {
        try {
            if (!employeeRepository.existsByEmail(email)) {
                throw new ResourceNotFoundException("Employee not found with email: " + email);
            }
            employeeRepository.deleteById(email);
            log.info("Deleted employee with email: {}", email);
        } catch (Exception e) {
            log.error("Error deleting employee with email {}: {}", email, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Employee> getByDepartment(String department) {
        try {
            List<Employee> employees = employeeRepository.findByDepartment(department);
            log.info("Retrieved {} employees from department: {}", employees.size(), department);
            return employees;
        } catch (Exception e) {
            log.error("Error retrieving employees by department {}: {}", department, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Employee> getByManagerEmail(String managerEmail) {
        try {
            List<Employee> employees = employeeRepository.findByManagerEmail(managerEmail);
            log.info("Retrieved {} employees managed by: {}", employees.size(), managerEmail);
            return employees;
        } catch (Exception e) {
            log.error("Error retrieving employees by manager email {}: {}", managerEmail, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try {
            boolean exists = employeeRepository.existsByEmail(email);
            log.info("Employee with email {} exists: {}", email, exists);
            return exists;
        } catch (Exception e) {
            log.error("Error checking if employee exists with email {}: {}", email, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean isManager(String emailRequester) {
        try {
            Employee employee = employeeRepository.findByEmail(emailRequester)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            return employee.getRole() == Employee_Role_Enum.MANAGER;
        } catch (Exception e) {
            // Ghi log hoặc xử lý lỗi cụ thể nếu cần
            return false;
        }
    }
}
