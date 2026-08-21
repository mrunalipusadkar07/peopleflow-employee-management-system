package com.mrunali.peopleflow.service.impl;

import com.mrunali.peopleflow.dto.EmployeeRequestDTO;
import com.mrunali.peopleflow.entity.Employee;
import java.util.List;
import java.util.Set;

import com.mrunali.peopleflow.exception.EmployeeNotFoundException;
import com.mrunali.peopleflow.mapper.EmployeeMapper;
import com.mrunali.peopleflow.repository.EmployeeRepository;
import com.mrunali.peopleflow.service.EmployeeService;
import com.mrunali.peopleflow.dto.EmployeeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of(
                    "id",
                    "firstName",
                    "lastName",
                    "department",
                    "designation",
                    "salary"
            );

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Page<EmployeeResponseDTO> getAllEmployees(
            int page, int size, String sortBy, String direction) {

        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new IllegalArgumentException(
                    "Invalid sort field: " + sortBy
            );
        }

        Sort.Direction sortDirection;
        if (direction.equalsIgnoreCase("asc")) {
            sortDirection = Sort.Direction.ASC;
        } else if (direction.equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        } else {
            throw new IllegalArgumentException(
                    "Invalid sort direction: " + direction
            );
        }

        Pageable pageable = PageRequest.of(
                page, size, Sort.by(sortDirection, sortBy));

        return employeeRepository.findAll(pageable)
                .map(employeeMapper::toResponseDTO);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id
                        )
                );
        return employeeMapper.toResponseDTO(employee);
    }

    @Override
    public List<EmployeeResponseDTO> searchEmployees(String name) {

        return employeeRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name)
                .stream()
                .map(employeeMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<EmployeeResponseDTO> searchEmployeesByDepartment(String department) {

        return employeeRepository
                .findByDepartmentContainingIgnoreCase(department)
                .stream()
                .map(employeeMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<EmployeeResponseDTO> searchEmployeesByDesignation(String designation) {

        return employeeRepository
                .findByDesignationContainingIgnoreCase(designation)
                .stream()
                .map(employeeMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Employee updateEmployee(EmployeeRequestDTO employeeRequestDTO, Long id) {

        Employee existingEmployee = employeeRepository.findById(id).
                orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with id: " + id)
                );
        employeeMapper.updateEntity(existingEmployee, employeeRequestDTO);

        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                        .orElseThrow(() ->
                                new EmployeeNotFoundException(
                                        "Employee not found with id: " + id
                                ));

        employeeRepository.delete(employee);
    }

}
