package com.devPath.user.service;

import com.devPath.user.model.User;
import com.devPath.user.model.dto.CreateUserRequest;
import com.devPath.user.model.dto.UpdateUserRequest;
import com.devPath.user.model.dto.UserResponse;
import com.devPath.user.repository.UserRepository;
import com.devPath.user.service.KeycloakServiceImpl;
import com.devPath.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KeycloakServiceImpl keycloakService;

    public UserServiceImpl(UserRepository userRepository, KeycloakServiceImpl keycloakService) {
        this.userRepository = userRepository;
        this.keycloakService = keycloakService;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        log.info("user exist ? : {}", userRepository.existsByEmail(request.getEmail()));
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setLinkedinUrl(request.getLinkedInURL());
        user.setLevel(request.getLevel());
        user.setGithubUrl(request.getGitHubURL());
        log.info("user: {}", user);

        String keycloakId = keycloakService.createUser(request);
        user.setKeycloakId(keycloakId);
        log.info("user: {}", user);
        User savedUser = userRepository.save(user);
        log.info("user saved : {}", savedUser);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setKeycloakId(keycloakId);




        return response;
    }

    @Override
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update only allowed fields
        user.setUsername(request.getUsername());
        user.setLinkedinUrl(request.getLinkedInURL());
        user.setGithubUrl(request.getGitHubURL());
        user.setLevel(request.getLevel());

        User updated = userRepository.save(user);
        keycloakService.updateUser(request);
        return mapToResponse(updated);
    }

    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Optional but recommended
        keycloakService.deleteUser(user.getKeycloakId());

        userRepository.delete(user);

    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setKeycloakId(user.getKeycloakId());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setGitHubURL(user.getGithubUrl());
        response.setLinkedInURL(user.getLinkedinUrl());
        response.setLevel(user.getLevel());
        return response;
    }
}