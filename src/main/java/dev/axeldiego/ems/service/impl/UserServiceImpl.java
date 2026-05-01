package dev.axeldiego.ems.service.impl;

import java.util.Collections;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.axeldiego.ems.dto.UserDto;
import dev.axeldiego.ems.entity.User;
import dev.axeldiego.ems.repository.UserRepository;
import dev.axeldiego.ems.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User saved = userRepository.save(user);
        return new UserDto(saved.getUsername(), null);
    }

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(authority));
    }
}
