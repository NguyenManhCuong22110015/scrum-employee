package com.example.demo.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Employees;

public interface Employee  extends JpaRepository<Employees, String> {
    Employees findByEmail(String email);

}
