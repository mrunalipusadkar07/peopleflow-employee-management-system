package com.mrunali.peopleflow.controller;

import com.mrunali.peopleflow.entity.Employee;
import com.mrunali.peopleflow.service.EmployeeService;
import com.mrunali.peopleflow.dto.EmployeeRequestDTO;
import com.mrunali.peopleflow.dto.EmployeeResponseDTO;
import com.mrunali.peopleflow.mapper.EmployeeMapper;
import org.springframework.web.bind.annotation.*;

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
    public EmployeeResponseDTO createEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {

        Employee employee = employeeMapper.toEntity(employeeRequestDTO);

        Employee savedEmployee = employeeService.saveEmployee(employee);
        return employeeMapper.toResponseDTO(savedEmployee);
    }

    @GetMapping
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeResponseDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public EmployeeResponseDTO updateEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO,
                                   @PathVariable Long id) {
        Employee employee = employeeService.updateEmployee(employeeRequestDTO, id);
        return employeeMapper.toResponseDTO(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
