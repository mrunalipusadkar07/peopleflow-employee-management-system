package com.mrunali.peopleflow.repository;

import com.mrunali.peopleflow.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    List<Employee> findByDepartmentContainingIgnoreCase(String department);
    List<Employee> findByDesignationContainingIgnoreCase(String designation);
}
