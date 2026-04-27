package dev.axeldiego.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.axeldiego.ems.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
