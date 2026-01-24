package com.devPath.auth;

import com.devPath.user.model.User;
import java.util.UUID;

public interface AuthService {
    //TODO : implementation AuthService
    User getCurrentUser();
    UUID getCurrentUserId();

}
