package dev.axeldiego.ems.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.axeldiego.ems.dto.DepartmentDto;
import dev.axeldiego.ems.entity.Department;
import dev.axeldiego.ems.exception.DepartmentAlreadyExistsException;
import dev.axeldiego.ems.exception.ResourceNotFoundException;
import dev.axeldiego.ems.repository.DepartmentRepository;
import dev.axeldiego.ems.service.impl.DepartmentServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("Department Service Tests")
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department department;
    private DepartmentDto departmentDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        department = new Department(1L, "Engineering");
        departmentDto = new DepartmentDto(1L, "Engineering");
    }

    @Test
    @DisplayName("Should create department when valid data provided")
    void testCreateDepartment_Success() {
        when(departmentRepository.existsDepartmentByNameIgnoreCase("Engineering")).thenReturn(false);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        DepartmentDto result = departmentService.createDepartment(departmentDto);

        assertNotNull(result);
        assertEquals("Engineering", result.getName());
        verify(departmentRepository, times(1)).existsDepartmentByNameIgnoreCase("Engineering");
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    @DisplayName("Should throw exception when department with same name exists")
    void testCreateDepartment_NameAlreadyExists() {
        when(departmentRepository.existsDepartmentByNameIgnoreCase("Engineering")).thenReturn(true);

        assertThrows(DepartmentAlreadyExistsException.class, () -> {
            departmentService.createDepartment(departmentDto);
        });

        verify(departmentRepository, times(1)).existsDepartmentByNameIgnoreCase("Engineering");
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    @DisplayName("Should get all departments")
    void testGetAllDepartments_Success() {
        Department dept2 = new Department(2L, "Sales");
        List<Department> departments = List.of(department, dept2);

        when(departmentRepository.findAll()).thenReturn(departments);

        List<DepartmentDto> result = departmentService.getAllDepartments();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get department by id")
    void testGetDepartmentById_Success() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        DepartmentDto result = departmentService.getDepartmentById(1L);

        assertNotNull(result);
        assertEquals("Engineering", result.getName());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when department not found by id")
    void testGetDepartmentById_NotFound() {
        when(departmentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            departmentService.getDepartmentById(999L);
        });

        verify(departmentRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should get department by name")
    void testGetDepartmentByName_Success() {
        when(departmentRepository.findByNameIgnoreCase("Engineering")).thenReturn(Optional.of(department));

        DepartmentDto result = departmentService.getDepartmentByName("Engineering");

        assertNotNull(result);
        assertEquals("Engineering", result.getName());
        verify(departmentRepository, times(1)).findByNameIgnoreCase("Engineering");
    }

    @Test
    @DisplayName("Should throw exception when department not found by name")
    void testGetDepartmentByName_NotFound() {
        when(departmentRepository.findByNameIgnoreCase("NonExistent")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            departmentService.getDepartmentByName("NonExistent");
        });

        verify(departmentRepository, times(1)).findByNameIgnoreCase("NonExistent");
    }

    @Test
    @DisplayName("Should update department successfully")
    void testUpdateDepartment_Success() {
        DepartmentDto updateDto = new DepartmentDto(1L, "Engineering");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.existsDepartmentByNameIgnoreCase("Engineering")).thenReturn(true);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        DepartmentDto result = departmentService.updateDepartment(1L, updateDto);

        assertNotNull(result);
        verify(departmentRepository, times(1)).findById(1L);
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    @DisplayName("Should throw exception when updating to existing name")
    void testUpdateDepartment_NameAlreadyExists() {
        Department existingDept = new Department(2L, "Sales");
        DepartmentDto updateDto = new DepartmentDto(1L, "Sales");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.existsDepartmentByNameIgnoreCase("Sales")).thenReturn(true);

        assertThrows(DepartmentAlreadyExistsException.class, () -> {
            departmentService.updateDepartment(1L, updateDto);
        });

        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    @DisplayName("Should delete department successfully")
    void testDeleteDepartment_Success() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        assertDoesNotThrow(() -> departmentService.deleteDepartment(1L));

        verify(departmentRepository, times(1)).findById(1L);
        verify(departmentRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent department")
    void testDeleteDepartment_NotFound() {
        when(departmentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            departmentService.deleteDepartment(999L);
        });

        verify(departmentRepository, never()).deleteById(any());
    }
}





