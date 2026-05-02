package dev.axeldiego.ems.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import dev.axeldiego.ems.dto.DepartmentDto;
import dev.axeldiego.ems.entity.Department;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Department Mapper Tests")
class DepartmentMapperTest {

    private Department department;
    private DepartmentDto departmentDto;

    @BeforeEach
    void setUp() {
        department = new Department(1L, "Engineering");
        departmentDto = new DepartmentDto(1L, "Engineering");
    }

    @Test
    @DisplayName("Should map Department to DepartmentDto")
    void testMapToDepartmentDto_Success() {
        DepartmentDto result = DepartmentMapper.mapToDepartmentDto(department);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Engineering", result.getName());
    }

    @Test
    @DisplayName("Should return null when mapping null Department")
    void testMapToDepartmentDto_NullDepartment() {
        DepartmentDto result = DepartmentMapper.mapToDepartmentDto(null);

        assertNull(result);
    }

    @Test
    @DisplayName("Should map DepartmentDto to Department")
    void testMapToDepartment_Success() {
        Department result = DepartmentMapper.mapToDepartment(departmentDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Engineering", result.getName());
    }

    @Test
    @DisplayName("Should return null when mapping null DepartmentDto")
    void testMapToDepartment_NullDepartmentDto() {
        Department result = DepartmentMapper.mapToDepartment(null);

        assertNull(result);
    }

    @Test
    @DisplayName("Should preserve all fields during bidirectional mapping")
    void testBidirectionalMapping() {
        // Department -> DepartmentDto -> Department
        DepartmentDto dto = DepartmentMapper.mapToDepartmentDto(department);
        Department result = DepartmentMapper.mapToDepartment(dto);

        assertNotNull(result);
        assertEquals(department.getId(), result.getId());
        assertEquals(department.getName(), result.getName());
    }

    @Test
    @DisplayName("Should map different department names correctly")
    void testMapDifferentDepartmentNames() {
        Department sales = new Department(2L, "Sales");
        Department hr = new Department(3L, "Human Resources");

        DepartmentDto salesDto = DepartmentMapper.mapToDepartmentDto(sales);
        DepartmentDto hrDto = DepartmentMapper.mapToDepartmentDto(hr);

        assertEquals("Sales", salesDto.getName());
        assertEquals("Human Resources", hrDto.getName());
    }
}






