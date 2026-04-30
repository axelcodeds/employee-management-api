package dev.axeldiego.ems.mapper;

import dev.axeldiego.ems.dto.EmployeeDto;
import dev.axeldiego.ems.dto.DepartmentDto;
import dev.axeldiego.ems.entity.Employee;
import dev.axeldiego.ems.entity.EmployeeStatus;

public class EmployeeMapper {
    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        DepartmentDto departmentDto = employee.getDepartment() != null
            ? DepartmentMapper.mapToDepartmentDto(employee.getDepartment())
            : null;

        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getSalary(),
                departmentDto,
                employee.getStatus(),
                employee.getCreatedAt(),
                employee.getUpdatedAt());
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setSalary(employeeDto.getSalary());
        employee.setDepartment(employeeDto.getDepartment() != null
            ? DepartmentMapper.mapToDepartment(employeeDto.getDepartment())
            : null);
        employee.setStatus(employeeDto.getStatus() != null ? employeeDto.getStatus() : EmployeeStatus.ACTIVE);
        employee.setCreatedAt(employeeDto.getCreatedAt());
        employee.setUpdatedAt(employeeDto.getUpdatedAt());
        return employee;
    }
}
