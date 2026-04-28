package dev.axeldiego.ems.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.axeldiego.ems.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    Page<EmployeeDto> getAllEmployees(Pageable pageable, String department);

    EmployeeDto getEmployeeById(Long employeeId);

    EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto);

    void deleteEmployee(Long employeeId);
}
