package com.example.demo.Service.Impl;

import com.example.demo.Repository.HolidayRepository;
import com.example.demo.Service.IHolidayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HolidayService implements IHolidayService {

    private final HolidayRepository holidayRepository;

    @Override
    public long calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        long workingDays = 0;
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            if (isWorkingDay(currentDate)) {
                workingDays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        log.info("Calculated {} working days between {} and {}", workingDays, startDate, endDate);
        return workingDays;
    }

    @Override
    public boolean isWorkingDay(LocalDate date) {
        return !isWeekend(date) && !isHoliday(date);
    }

    @Override
    public List<LocalDate> getHolidaysInRange(LocalDate startDate, LocalDate endDate) {
        return holidayRepository.findHolidayDatesBetween(startDate, endDate);
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        return holidayRepository.existsByDate(date);
    }

    @Override
    public boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
