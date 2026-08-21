package com.mrunali.peopleflow.controller;

import com.mrunali.peopleflow.entity.Employee;
import com.mrunali.peopleflow.service.EmployeeService;
import com.mrunali.peopleflow.dto.EmployeeRequestDTO;
import com.mrunali.peopleflow.dto.EmployeeResponseDTO;
import com.mrunali.peopleflow.mapper.EmployeeMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {

        Employee employee = employeeMapper.toEntity(employeeRequestDTO);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        EmployeeResponseDTO employeeResponseDTO = employeeMapper.toResponseDTO(savedEmployee);

        return new ResponseEntity<>(employeeResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public Page<EmployeeResponseDTO> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return employeeService.getAllEmployees(
                page, size, sortBy, direction);
    }

    @GetMapping("/search")
    public List<EmployeeResponseDTO> searchEmployees(
            @RequestParam String name) {
        return employeeService.searchEmployees(name);
    }

    @GetMapping("/search/department")
    public List<EmployeeResponseDTO> searchEmployeesByDepartment(
            @RequestParam String department) {

        return employeeService.searchEmployeesByDepartment(department);
    }

    @GetMapping("/search/designation")
    public List<EmployeeResponseDTO> searchEmployeesByDesignation(
            @RequestParam String designation) {

        return employeeService.searchEmployeesByDesignation(designation);
    }

    @GetMapping("/{id}")
    public EmployeeResponseDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public EmployeeResponseDTO updateEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO,
                                   @PathVariable Long id) {
        Employee employee = employeeService.updateEmployee(employeeRequestDTO, id);
        return employeeMapper.toResponseDTO(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
