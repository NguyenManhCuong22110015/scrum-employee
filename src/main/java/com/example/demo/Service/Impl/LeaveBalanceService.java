package com.example.demo.Service.Impl;

import com.example.demo.Exception.InsufficientLeaveBalanceException;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.Employee;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Service.IHolidayService;
import com.example.demo.Service.ILeaveBalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LeaveBalanceService implements ILeaveBalanceService {

    private final EmployeeRepository employeeRepository;
    private final IHolidayService holidayService;

    @Override
    public boolean hasEnoughLeaveBalance(String employeeEmail, long requiredDays) {
        Employee employee = getEmployeeByEmail(employeeEmail);
        return employee.getLeaveBalance() >= requiredDays;
    }

    @Override
    public void deductLeaveBalance(String employeeEmail, long days) {
        Employee employee = getEmployeeByEmail(employeeEmail);
        
        if (employee.getLeaveBalance() < days) {
            throw new InsufficientLeaveBalanceException(
                String.format("Insufficient leave balance. Required: %d days, Available: %d days", 
                    days, employee.getLeaveBalance())
            );
        }
        
        employee.setLeaveBalance(employee.getLeaveBalance() - (int) days);
        employeeRepository.save(employee);
        
        log.info("Deducted {} days from employee {}, remaining balance: {}", 
                days, employeeEmail, employee.getLeaveBalance());
    }

    @Override
    public void restoreLeaveBalance(String employeeEmail, long days) {
        Employee employee = getEmployeeByEmail(employeeEmail);
        employee.setLeaveBalance(employee.getLeaveBalance() + (int) days);
        employeeRepository.save(employee);
        
        log.info("Restored {} days to employee {}, new balance: {}", 
                days, employeeEmail, employee.getLeaveBalance());
    }

    @Override
    public int getCurrentLeaveBalance(String employeeEmail) {
        Employee employee = getEmployeeByEmail(employeeEmail);
        return employee.getLeaveBalance();
    }

    @Override
    public void resetAnnualLeaveBalance(String employeeEmail, int newBalance) {
        Employee employee = getEmployeeByEmail(employeeEmail);
        employee.setLeaveBalance(newBalance);
        employeeRepository.save(employee);
        
        log.info("Reset leave balance for employee {} to {} days", employeeEmail, newBalance);
    }

    @Override
    public long calculateActualLeaveDays(LocalDate startDate, LocalDate endDate) {
        return holidayService.calculateWorkingDays(startDate, endDate);
    }

    private Employee getEmployeeByEmail(String email) {
        return employeeRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email: " + email));
    }
}
