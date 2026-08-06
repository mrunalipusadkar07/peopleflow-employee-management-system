package com.mrunali.peopleflow.repository;

import com.mrunali.peopleflow.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
