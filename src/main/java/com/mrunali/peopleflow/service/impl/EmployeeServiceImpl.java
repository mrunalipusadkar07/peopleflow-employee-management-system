package com.mrunali.peopleflow.service.impl;

import com.mrunali.peopleflow.dto.EmployeeRequestDTO;
import com.mrunali.peopleflow.entity.Employee;
import java.util.List;

import com.mrunali.peopleflow.exception.EmployeeNotFoundException;
import com.mrunali.peopleflow.repository.EmployeeRepository;
import com.mrunali.peopleflow.service.EmployeeService;
import com.mrunali.peopleflow.dto.EmployeeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );
        return convertToResponseDTO(employee);
    }

    @Override
    public Employee updateEmployee(EmployeeRequestDTO employeeRequestDTO, Long id) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);

        if (existingEmployee != null) {
            existingEmployee.setFirstName(employeeRequestDTO.getFirstName());
            existingEmployee.setLastName(employeeRequestDTO.getLastName());
            existingEmployee.setEmail(employeeRequestDTO.getEmail());
            existingEmployee.setPhone(employeeRequestDTO.getPhone());
            existingEmployee.setDepartment(employeeRequestDTO.getDepartment());
            existingEmployee.setDesignation(employeeRequestDTO.getDesignation());
            existingEmployee.setSalary(employeeRequestDTO.getSalary());

            return employeeRepository.save(existingEmployee);
        }
        return null;
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeResponseDTO convertToResponseDTO(Employee employee) {
        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getDepartment(),
                employee.getDesignation(),
                employee.getSalary()
        );
    }
}
