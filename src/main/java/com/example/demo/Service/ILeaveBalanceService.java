package com.example.demo.Service;

import java.time.LocalDate;

public interface ILeaveBalanceService {
    
    /**
     * Kiểm tra employee có đủ số ngày phép không
     */
    boolean hasEnoughLeaveBalance(String employeeEmail, long requiredDays);
    
    /**
     * Trừ số ngày phép của employee
     */
    void deductLeaveBalance(String employeeEmail, long days);
    
    /**
     * Hoàn trả số ngày phép (khi reject/cancel request)
     */
    void restoreLeaveBalance(String employeeEmail, long days);
    
    /**
     * Lấy số ngày phép còn lại
     */
    int getCurrentLeaveBalance(String employeeEmail);
    
    /**
     * Reset số ngày phép hàng năm
     */
    void resetAnnualLeaveBalance(String employeeEmail, int newBalance);
    
    /**
     * Tính số ngày leave thực tế (chỉ tính working days)
     */
    long calculateActualLeaveDays(LocalDate startDate, LocalDate endDate);
}
