package com.devPath.user.model.dto;

import com.devPath.user.ressources.Level;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Data
public class CreateUserRequest {
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
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

    @NotNull
    private Set<String> roles; // e.g. ["USER", "ADMIN"]

}
