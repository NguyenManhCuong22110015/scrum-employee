package com.example.demo.Service;

import java.time.LocalDate;
import java.util.List;

public interface IHolidayService {
    
    /**
     * Tính số ngày làm việc giữa 2 ngày (không bao gồm cuối tuần và ngày lễ)
     */
    long calculateWorkingDays(LocalDate startDate, LocalDate endDate);
    
    /**
     * Kiểm tra ngày có phải là working day không
     */
    boolean isWorkingDay(LocalDate date);
    
    /**
     * Lấy danh sách ngày lễ trong khoảng thời gian
     */
    List<LocalDate> getHolidaysInRange(LocalDate startDate, LocalDate endDate);
    
    /**
     * Kiểm tra có phải ngày lễ không
     */
    boolean isHoliday(LocalDate date);
    
    /**
     * Kiểm tra có phải cuối tuần không
     */
    boolean isWeekend(LocalDate date);
}
