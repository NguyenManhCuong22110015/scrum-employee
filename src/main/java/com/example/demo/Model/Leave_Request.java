package com.example.demo.Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.example.demo.Enum.Leave_Status_Enum;
import com.example.demo.Enum.Leave_Type_Enum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

@Entity
@Table(name = "leave_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Leave_Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(name = "employee_email", nullable = false)
    private String employeeEmail;

    @Enumerated(EnumType.STRING)
    private Leave_Type_Enum leaveType;

    @Column(name = "start_date", nullable = false, updatable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false, updatable = false)
    private LocalDate endDate;

    @Column(name = "reason", nullable = false, length = 1000)
    private String reason;

    @Enumerated(EnumType.STRING)
    private Leave_Status_Enum leaveStatus = Leave_Status_Enum.PENDING;

    @Column(name = "approved_at", nullable = true)
    private Timestamp approvedAt;

    @Column(name = "create_at", nullable = false, updatable = false)
    private Timestamp createAt = new Timestamp(System.currentTimeMillis());

    @Transient
    private long total;

    public long getTotal() {
        if (startDate != null && endDate != null) {
            return ChronoUnit.DAYS.between(startDate, endDate) + 1;
        }
        return 0;
    }
}
