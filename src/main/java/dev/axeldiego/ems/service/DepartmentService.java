package dev.axeldiego.ems.service;

import dev.axeldiego.ems.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto createDepartment(DepartmentDto departmentDto);

    List<DepartmentDto> getAllDepartments();

    DepartmentDto getDepartmentById(Long departmentId);

    DepartmentDto getDepartmentByName(String name);

    DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto);

    void deleteDepartment(Long departmentId);
}
