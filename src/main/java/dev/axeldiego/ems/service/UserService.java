package dev.axeldiego.ems.service;

import dev.axeldiego.ems.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUsername(String username);
}
