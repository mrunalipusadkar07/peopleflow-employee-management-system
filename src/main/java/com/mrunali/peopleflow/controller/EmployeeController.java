package com.mrunali.peopleflow.controller;

import com.mrunali.peopleflow.entity.Employee;
import com.mrunali.peopleflow.service.EmployeeService;
import com.mrunali.peopleflow.dto.EmployeeRequestDTO;
import com.mrunali.peopleflow.dto.EmployeeResponseDTO;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeResponseDTO createEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {

        Employee employee = new Employee();

        employee.setFirstName(employeeRequestDTO.getFirstName());
        employee.setLastName(employeeRequestDTO.getLastName());
        employee.setEmail(employeeRequestDTO.getEmail());
        employee.setPhone(employeeRequestDTO.getPhone());
        employee.setDepartment(employeeRequestDTO.getDepartment());
        employee.setDesignation(employeeRequestDTO.getDesignation());
        employee.setSalary(employeeRequestDTO.getSalary());

        Employee savedEmployee = employeeService.saveEmployee(employee);
        return employeeService.convertToResponseDTO(savedEmployee);
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
        return employeeService.convertToResponseDTO(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
