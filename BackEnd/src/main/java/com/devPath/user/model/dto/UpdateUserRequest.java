package com.devPath.user.model.dto;

import com.devPath.user.ressources.Level;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserRequest {

    private String username;

    @Email
    private String email;


    private Level level;

    private String bio;

    @Pattern(
            regexp = "^(https?://)?(www\\.)?github\\.com/.+",
            message = "Invalid GitHub URL"
    )
    private String gitHubURL;

    @Pattern(
            regexp = "^(https?://)?(www\\.)?linkedin\\.com/(in|company)/.+",
            message = "Invalid LinkedIn URL"
    )
    private String linkedInURL;
    private String KeycloakId;

    private Set<String> roles; // optional update
}
