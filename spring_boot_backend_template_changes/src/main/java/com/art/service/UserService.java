package com.art.service;

import java.util.List;
import java.util.Optional;

import com.art.dto.UserDto;
import com.art.pojos.User;

public interface UserService {
    List<UserDto> getAllUsers();
    String addUser(UserDto userDTO);
    String deleteUser(Long userId);
   UserDto getUserDetails(Long userId);
    String updateUser(Long userId, UserDto userDTO);
    Optional<User> findByEmail(String email);
    Optional<User> loginUser (String email, String password);

}