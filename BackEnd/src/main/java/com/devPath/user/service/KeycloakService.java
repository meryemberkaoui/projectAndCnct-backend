package com.devPath.user.service;

import com.devPath.user.model.dto.CreateUserRequest;
import com.devPath.user.model.dto.UpdateUserRequest;

public interface KeycloakService {
    String createUser(CreateUserRequest request);

    void deleteUser(String keycloakUserId);


    void updateUser(UpdateUserRequest request);
}
