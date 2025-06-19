package com.example.demo.Repository;

import com.example.demo.Model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, UUID> {
    
    List<Holiday> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    boolean existsByDate(LocalDate date);
    
    @Query("SELECT h.date FROM Holiday h WHERE h.date BETWEEN :startDate AND :endDate")
    List<LocalDate> findHolidayDatesBetween(@Param("startDate") LocalDate startDate, 
                                           @Param("endDate") LocalDate endDate);
}
