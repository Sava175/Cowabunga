package com.cowabunga.services;

import com.cowabunga.domain.User;
import com.cowabunga.dto.UserDto;
import com.cowabunga.dto.UserUpdateDto;

public interface UserService {
    UserDto findUserByEmail(String email);
    UserDto signup(UserDto userDto);
    UserUpdateDto updateUser(User userCurrent, User userForm);
    User getCurrentUser();
    void deleteUser(Integer id);
    UserDto blockingUser(String email);
}
