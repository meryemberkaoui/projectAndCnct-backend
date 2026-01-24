package com.devPath.auth;

import com.devPath.user.model.User;
import com.devPath.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        //TODO

        return null;
    }

    @Override
    public UUID getCurrentUserId() {
        return getCurrentUser().getId();
    }
}

