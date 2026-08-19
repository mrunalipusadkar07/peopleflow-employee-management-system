package com.mrunali.peopleflow.service;

import com.mrunali.peopleflow.dto.EmployeeRequestDTO;
import com.mrunali.peopleflow.entity.Employee;
import com.mrunali.peopleflow.dto.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<EmployeeResponseDTO> getAllEmployees();

    EmployeeResponseDTO getEmployeeById(Long id);

    List<EmployeeResponseDTO> searchEmployees(String name);

    List<EmployeeResponseDTO> searchEmployeesByDepartment(String department);

    List<EmployeeResponseDTO> searchEmployeesByDesignation(String designation);

    Employee updateEmployee(EmployeeRequestDTO employeeRequestDTO, Long id);

    void deleteEmployee(Long id);
}
