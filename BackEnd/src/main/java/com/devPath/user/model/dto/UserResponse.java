package com.devPath.user.model.dto;

import com.devPath.user.ressources.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private String bio;
    private Level level;
    private String gitHubURL;
    private String linkedInURL;
    private Set<String> roles;
    private String keycloakId;

}
