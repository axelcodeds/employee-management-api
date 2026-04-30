package dev.axeldiego.ems.service.impl;

import dev.axeldiego.ems.dto.DepartmentDto;
import dev.axeldiego.ems.entity.Department;
import dev.axeldiego.ems.exception.DepartmentAlreadyExistsException;
import dev.axeldiego.ems.exception.ResourceNotFoundException;
import dev.axeldiego.ems.mapper.DepartmentMapper;
import dev.axeldiego.ems.repository.DepartmentRepository;
import dev.axeldiego.ems.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        if (departmentRepository.existsDepartmentByNameIgnoreCase(departmentDto.getName())) {
            throw new DepartmentAlreadyExistsException("Department with this name already exists");
        }

        Department department = DepartmentMapper.mapToDepartment(departmentDto);
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(DepartmentMapper::mapToDepartmentDto)
                .toList();
    }

    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
        return DepartmentMapper.mapToDepartmentDto(department);
    }

    @Override
    public DepartmentDto getDepartmentByName(String name) {
        Department department = departmentRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with name: " + name));
        return DepartmentMapper.mapToDepartmentDto(department);
    }

    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));

        if (!department.getName().equalsIgnoreCase(departmentDto.getName())
                && departmentRepository.existsDepartmentByNameIgnoreCase(departmentDto.getName())) {
            throw new DepartmentAlreadyExistsException("Department with this name already exists");
        }

        department.setName(departmentDto.getName());
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
        departmentRepository.deleteById(departmentId);
    }
}
