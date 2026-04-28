package dev.axeldiego.ems.config;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import dev.axeldiego.ems.entity.Employee;
import dev.axeldiego.ems.repository.EmployeeRepository;

@Configuration
@Profile("dev")
public class MockEmployeeDataLoader {

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
    CommandLineRunner seedMockEmployees(EmployeeRepository employeeRepository) {
        return args -> {
            if (employeeRepository.count() > 0) {
                return;
            }

            List<Employee> employees = new ArrayList<>();

            for (int i = 1; i <= 50; i++) {
                String firstName = FIRST_NAMES[(i - 1) % FIRST_NAMES.length];
                String lastName = LAST_NAMES[(i - 1) / FIRST_NAMES.length];

                employees.add(new Employee(
                        null,
                        firstName,
                        lastName,
                        firstName.toLowerCase() + "." + lastName.toLowerCase() + i + "@example.com",
                        BigDecimal.valueOf(40000L + (i * 1000L)).setScale(2, RoundingMode.UNNECESSARY),
                        DEPARTMENTS[(i - 1) % DEPARTMENTS.length]));
            }

            employeeRepository.saveAll(employees);
        };
    }
}







