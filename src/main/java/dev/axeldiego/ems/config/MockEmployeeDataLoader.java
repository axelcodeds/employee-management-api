package dev.axeldiego.ems.config;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import dev.axeldiego.ems.entity.Department;
import dev.axeldiego.ems.entity.Employee;
import dev.axeldiego.ems.entity.EmployeeStatus;
import dev.axeldiego.ems.entity.User;
import dev.axeldiego.ems.entity.UserRole;
import dev.axeldiego.ems.repository.DepartmentRepository;
import dev.axeldiego.ems.repository.EmployeeRepository;
import dev.axeldiego.ems.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class MockEmployeeDataLoader {

    @Value("${EMS_ADMIN_PASSWORD:admin123}")
    private String adminPassword;

    private static final String[] FIRST_NAMES = {
            "John",
            "Jane",
            "Michael",
            "Sarah",
            "David",
            "Emily",
            "Robert",
            "Jessica",
            "Daniel",
            "Laura"
    };

    private static final String[] LAST_NAMES = {
            "Anderson",
            "Bennett",
            "Carter",
            "Diaz",
            "Edwards"
    };

    private static final String[] DEPARTMENTS = {
            "Engineering",
            "HR",
            "Finance",
            "Sales",
            "Marketing",
            "Operations"
    };

    @Bean
    CommandLineRunner seedMockEmployees(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository,
            UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            employeeRepository.deleteAll();
            departmentRepository.deleteAll();
            userRepository.deleteAll();

            // Seed test users
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setRole(UserRole.ADMIN);
            userRepository.save(adminUser);

            User regularUser = new User();
            regularUser.setUsername("user");
            regularUser.setPassword(passwordEncoder.encode("user123"));
            regularUser.setRole(UserRole.USER);
            userRepository.save(regularUser);

            // Seed departments
            List<Department> departments = new ArrayList<>();
            for (String deptName : DEPARTMENTS) {
                departments.add(new Department(null, deptName));
            }
            Map<String, Department> savedDepartments = new HashMap<>();
            for (Department dept : departments) {
                savedDepartments.put(dept.getName(), departmentRepository.save(dept));
            }

            // Seed employees
            List<Employee> employees = new ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                String firstName = FIRST_NAMES[(i - 1) % FIRST_NAMES.length];
                String lastName = LAST_NAMES[(i - 1) / FIRST_NAMES.length];
                EmployeeStatus status = (i <= 40) ? EmployeeStatus.ACTIVE : EmployeeStatus.INACTIVE;
                String deptName = DEPARTMENTS[(i - 1) % DEPARTMENTS.length];

                employees.add(new Employee(
                        null,
                        firstName,
                        lastName,
                        firstName.toLowerCase() + "." + lastName.toLowerCase() + i + "@example.com",
                        BigDecimal.valueOf(40000L + (i * 1000L)).setScale(2, RoundingMode.UNNECESSARY),
                        savedDepartments.get(deptName),
                        status,
                        null,
                        null));
            }

            employeeRepository.saveAll(employees);
        };
    }
}
