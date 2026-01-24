package com.devPath.user.service;

import com.devPath.user.model.User;
import com.devPath.user.model.dto.CreateUserRequest;
import com.devPath.user.model.dto.UpdateUserRequest;
import com.devPath.user.model.dto.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(UUID id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(UUID id, UpdateUserRequest request);

    void deleteUser(UUID id);

}
