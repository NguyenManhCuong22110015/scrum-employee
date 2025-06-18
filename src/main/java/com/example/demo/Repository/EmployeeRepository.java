package com.example.demo.Repository;

import com.example.demo.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    
    Optional<Employee> findByEmail(String email);
    
    List<Employee> findByDepartment(String department);
    
    List<Employee> findByManagerEmail(String managerEmail);
    
    boolean existsByEmail(String email);
}
