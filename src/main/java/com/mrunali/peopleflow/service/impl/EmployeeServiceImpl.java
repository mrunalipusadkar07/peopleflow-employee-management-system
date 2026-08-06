package com.mrunali.peopleflow.service.impl;

import com.mrunali.peopleflow.repository.EmployeeRepository;
import com.mrunali.peopleflow.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
}
