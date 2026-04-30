package dev.axeldiego.ems.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.axeldiego.ems.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByNameIgnoreCase(String name);

    boolean existsDepartmentByNameIgnoreCase(String name);
}

