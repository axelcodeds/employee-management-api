package dev.axeldiego.ems.mapper;

import dev.axeldiego.ems.dto.DepartmentDto;
import dev.axeldiego.ems.entity.Department;

public class DepartmentMapper {

    public static DepartmentDto mapToDepartmentDto(Department department) {
        if (department == null) {
            return null;
        }

        return new DepartmentDto(
                department.getId(),
                department.getName());
    }

    public static Department mapToDepartment(DepartmentDto departmentDto) {
        if (departmentDto == null) {
            return null;
        }

        Department department = new Department();
        department.setId(departmentDto.getId());
        department.setName(departmentDto.getName());
        return department;
    }
}
