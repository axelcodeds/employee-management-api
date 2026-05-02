package dev.axeldiego.ems.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import dev.axeldiego.ems.dto.DepartmentDto;
import dev.axeldiego.ems.dto.EmployeeDto;
import dev.axeldiego.ems.entity.Department;
import dev.axeldiego.ems.entity.Employee;
import dev.axeldiego.ems.entity.EmployeeStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Employee Mapper Tests")
class EmployeeMapperTest {

    private Employee employee;
    private EmployeeDto employeeDto;
    private Department department;
    private DepartmentDto departmentDto;

    @BeforeEach
    void setUp() {
        department = new Department(1L, "Engineering");
        departmentDto = new DepartmentDto(1L, "Engineering");

        LocalDateTime now = LocalDateTime.now();
        employee = new Employee(
                1L,
                "John",
                "Doe",
                "john.doe@example.com",
                new BigDecimal("50000.00"),
                department,
                EmployeeStatus.ACTIVE,
                now,
                now
        );

        employeeDto = new EmployeeDto(
                1L,
                "John",
                "Doe",
                "john.doe@example.com",
                new BigDecimal("50000.00"),
                departmentDto,
                EmployeeStatus.ACTIVE,
                now,
                now
        );
    }

    @Test
    @DisplayName("Should map Employee to EmployeeDto")
    void testMapToEmployeeDto_Success() {
        EmployeeDto result = EmployeeMapper.mapToEmployeeDto(employee);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals(new BigDecimal("50000.00"), result.getSalary());
        assertEquals(EmployeeStatus.ACTIVE, result.getStatus());
        assertNotNull(result.getDepartment());
        assertEquals("Engineering", result.getDepartment().getName());
    }

    @Test
    @DisplayName("Should return null when mapping null Employee")
    void testMapToEmployeeDto_NullEmployee() {
        EmployeeDto result = EmployeeMapper.mapToEmployeeDto(null);

        assertNull(result);
    }

    @Test
    @DisplayName("Should handle null department when mapping to EmployeeDto")
    void testMapToEmployeeDto_NullDepartment() {
        employee.setDepartment(null);

        EmployeeDto result = EmployeeMapper.mapToEmployeeDto(employee);

        assertNotNull(result);
        assertNull(result.getDepartment());
    }

    @Test
    @DisplayName("Should map EmployeeDto to Employee")
    void testMapToEmployee_Success() {
        Employee result = EmployeeMapper.mapToEmployee(employeeDto);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals(new BigDecimal("50000.00"), result.getSalary());
        assertEquals(EmployeeStatus.ACTIVE, result.getStatus());
    }

    @Test
    @DisplayName("Should return null when mapping null EmployeeDto")
    void testMapToEmployee_NullEmployeeDto() {
        Employee result = EmployeeMapper.mapToEmployee(null);

        assertNull(result);
    }

    @Test
    @DisplayName("Should set default status to ACTIVE when null")
    void testMapToEmployee_DefaultStatusWhenNull() {
        employeeDto.setStatus(null);

        Employee result = EmployeeMapper.mapToEmployee(employeeDto);

        assertNotNull(result);
        assertEquals(EmployeeStatus.ACTIVE, result.getStatus());
    }

    @Test
    @DisplayName("Should preserve all fields during bidirectional mapping")
    void testBidirectionalMapping() {
        // Employee -> EmployeeDto -> Employee
        EmployeeDto dto = EmployeeMapper.mapToEmployeeDto(employee);
        Employee result = EmployeeMapper.mapToEmployee(dto);

        assertNotNull(result);
        assertEquals(employee.getFirstName(), result.getFirstName());
        assertEquals(employee.getLastName(), result.getLastName());
        assertEquals(employee.getEmail(), result.getEmail());
        assertEquals(employee.getSalary(), result.getSalary());
        assertEquals(employee.getStatus(), result.getStatus());
    }
}


