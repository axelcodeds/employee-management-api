package dev.axeldiego.ems.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.axeldiego.ems.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	boolean existsByEmail(String email);

	boolean existsByEmailAndIdNot(String email, Long id);

	@Query("SELECT e FROM Employee e WHERE LOWER(e.department.name) = LOWER(:departmentName)")
	Page<Employee> findByDepartmentNameIgnoreCase(@Param("departmentName") String departmentName, Pageable pageable);

}
