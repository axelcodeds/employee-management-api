package dev.axeldiego.ems.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import dev.axeldiego.ems.dto.DepartmentDto;
import dev.axeldiego.ems.dto.EmployeeDto;
import dev.axeldiego.ems.entity.Department;
import dev.axeldiego.ems.entity.Employee;
import dev.axeldiego.ems.entity.EmployeeStatus;
import dev.axeldiego.ems.exception.EmployeeAlreadyExistsException;
import dev.axeldiego.ems.exception.ResourceNotFoundException;
import dev.axeldiego.ems.mapper.EmployeeMapper;
import dev.axeldiego.ems.repository.EmployeeRepository;
import dev.axeldiego.ems.service.impl.EmployeeServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Employee Service Tests")
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDto employeeDto;
    private Department department;
    private DepartmentDto departmentDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        department = new Department(1L, "Engineering");
        departmentDto = new DepartmentDto(1L, "Engineering");

        employee = new Employee(
                1L,
                "John",
                "Doe",
                "john.doe@example.com",
                new BigDecimal("50000.00"),
                department,
                EmployeeStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        employeeDto = new EmployeeDto(
                1L,
                "John",
                "Doe",
                "john.doe@example.com",
                new BigDecimal("50000.00"),
                departmentDto,
                EmployeeStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("Should create employee when valid data provided")
    void testCreateEmployee_Success() {
        when(employeeRepository.existsByEmail(anyString())).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDto result = employeeService.createEmployee(employeeDto);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(employeeRepository, times(1)).existsByEmail("john.doe@example.com");
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("Should throw exception when employee with same email exists")
    void testCreateEmployee_EmailAlreadyExists() {
        when(employeeRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(EmployeeAlreadyExistsException.class, () -> {
            employeeService.createEmployee(employeeDto);
        });

        verify(employeeRepository, times(1)).existsByEmail("john.doe@example.com");
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Should get all employees with pagination")
    void testGetAllEmployees_WithPagination() {
        List<Employee> employees = List.of(employee);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Employee> pageEmployees = new PageImpl<>(employees);

        when(employeeRepository.findAll(pageable)).thenReturn(pageEmployees);

        Page<EmployeeDto> result = employeeService.getAllEmployees(pageable, null);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(employeeRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should filter employees by department")
    void testGetAllEmployees_FilterByDepartment() {
        List<Employee> employees = List.of(employee);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Employee> pageEmployees = new PageImpl<>(employees);

        when(employeeRepository.findByDepartmentNameIgnoreCase("Engineering", pageable))
                .thenReturn(pageEmployees);

        Page<EmployeeDto> result = employeeService.getAllEmployees(pageable, "Engineering");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(employeeRepository, times(1)).findByDepartmentNameIgnoreCase("Engineering", pageable);
    }

    @Test
    @DisplayName("Should get employee by id")
    void testGetEmployeeById_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeDto result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals("john.doe@example.com", result.getEmail());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when employee not found")
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(999L);
        });

        verify(employeeRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should update employee successfully")
    void testUpdateEmployee_Success() {
        EmployeeDto updateDto = new EmployeeDto(
                1L,
                "Jane",
                "Smith",
                "jane.smith@example.com",
                new BigDecimal("60000.00"),
                departmentDto,
                EmployeeStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsByEmailAndIdNot("jane.smith@example.com", 1L)).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDto result = employeeService.updateEmployee(1L, updateDto);

        assertNotNull(result);
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("Should throw exception when email already exists during update")
    void testUpdateEmployee_EmailAlreadyExists() {
        EmployeeDto updateDto = new EmployeeDto(
                1L,
                "Jane",
                "Smith",
                "existing.email@example.com",
                new BigDecimal("60000.00"),
                departmentDto,
                EmployeeStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsByEmailAndIdNot("existing.email@example.com", 1L)).thenReturn(true);

        assertThrows(EmployeeAlreadyExistsException.class, () -> {
            employeeService.updateEmployee(1L, updateDto);
        });

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Should delete employee successfully")
    void testDeleteEmployee_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        assertDoesNotThrow(() -> employeeService.deleteEmployee(1L));

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent employee")
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.deleteEmployee(999L);
        });

        verify(employeeRepository, never()).deleteById(any());
    }
}


