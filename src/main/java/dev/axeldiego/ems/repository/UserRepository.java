package dev.axeldiego.ems.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.axeldiego.ems.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
