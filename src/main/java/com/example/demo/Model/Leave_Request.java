package com.example.demo.Model;

import com.example.demo.Enum.Leave_Status_Enum;
import com.example.demo.Enum.Leave_Type_Enum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "leave_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leave_Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(name = "employee_email", nullable = false)
    private String employee_email;

    @Enumerated(EnumType.STRING)
    private Leave_Type_Enum leave_type;

    @Column(name = "start_date", nullable = false, updatable = false)
    private LocalDate start_date;

    @Column(name = "end_date", nullable = false, updatable = false)
    private LocalDate end_date;

    @Column(name = "reason", nullable = false, length = 1000)
    private String reason;

    @Enumerated(EnumType.STRING)
    private Leave_Status_Enum leave_status = Leave_Status_Enum.PENDING;

    @Column(name = "approved_at", nullable = false, updatable = false)
    private Timestamp approved_at;

    @Column(name = "create_at", nullable = false, updatable = false)
    private Timestamp create_at = new Timestamp(System.currentTimeMillis());

    @Transient
    private long total;

    public long getTotal() {
        if (start_date != null && end_date != null) {
            return ChronoUnit.DAYS.between(start_date, end_date) + 1;
        }
        return 0;
    }
}
