package dev.axeldiego.ems.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.axeldiego.ems.dto.EmployeeDto;
import dev.axeldiego.ems.entity.Employee;
import dev.axeldiego.ems.exception.EmployeeAlreadyExistsException;
import dev.axeldiego.ems.exception.ResourceNotFoundException;
import dev.axeldiego.ems.mapper.EmployeeMapper;
import dev.axeldiego.ems.repository.EmployeeRepository;
import dev.axeldiego.ems.service.EmployeeService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
            throw new EmployeeAlreadyExistsException("Employee with this email already exists");
        }

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public Page<EmployeeDto> getAllEmployees(Pageable pageable, String department) {
        Page<Employee> employees = (department == null || department.isBlank())
                ? employeeRepository.findAll(pageable)
                : employeeRepository.findByDepartmentIgnoreCase(department.trim(), pageable);

        return employees
                .map(EmployeeMapper::mapToEmployeeDto);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        if (employeeRepository.existsByEmailAndIdNot(employeeDto.getEmail(), employeeId)) {
            throw new EmployeeAlreadyExistsException("Employee with this email already exists");
        }

        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setSalary(employeeDto.getSalary());
        employee.setDepartment(employeeDto.getDepartment());

        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        employeeRepository.deleteById(employeeId);
    }

}
