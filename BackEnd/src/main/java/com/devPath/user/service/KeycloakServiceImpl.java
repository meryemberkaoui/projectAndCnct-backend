package com.devPath.user.service;

import com.devPath.user.model.dto.CreateUserRequest;
import com.devPath.user.model.dto.UpdateUserRequest;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.springframework.stereotype.Service;

import jakarta.ws.rs.core.Response;

@Slf4j
@Service
public class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloak;
    private static final String ADMIN_REALM = "master";
    private static final String USER_REALM = "devpath";



    public KeycloakServiceImpl() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")
                .realm(ADMIN_REALM) // admin realm
                .clientId("admin-cli")
                .username("admin") // keycloak admin username
                .password("admin")
                .build();
    }

    @Override
    public String createUser(CreateUserRequest request) {

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(request.getEmail());
        kcUser.setEmail(request.getEmail());
        kcUser.setEnabled(true);

        Response response = keycloak.realm(USER_REALM).users().create(kcUser);
        System.out.println(response.getStatus());
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user in Keycloak : " + response.getStatus());
        }

        String userId = CreatedResponseUtil.getCreatedId(response);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(request.getPassword());
        log.info("userid : " + userId);

        keycloak.realm(USER_REALM)
                .users()
                .get(userId)
                .resetPassword(passwordCred);
        log.info("userid : " + userId);

//        List<RoleRepresentation> roles = request.getRoles().stream()
//                .map(roleName -> keycloak.realm("devpath").roles().get(roleName).toRepresentation())
//                .collect(Collectors.toList());

//        keycloak.realm("devpath")
//                .users()
//                .get(userId)
//                .roles()
//                .realmLevel()
//                .add(roles);

        return userId;
    }
    @Override
    public void deleteUser(String keycloakUserId) {

        log.info("Deleting Keycloak user with id={}", keycloakUserId);

        Response response = keycloak
                .realm(USER_REALM) // on doit utiliser le user relam ici
                .users()
                .delete(keycloakUserId);

        if (response.getStatus() != 204) {
            throw new RuntimeException(
                    "Failed to delete user in Keycloak, status: " + response.getStatus()
            );
        }

        log.info("Keycloak user deleted successfully: {}", keycloakUserId);
    }
    @Override
    public void updateUser(UpdateUserRequest request) {

        log.info("Updating Keycloak user with id={}", request.getKeycloakId());

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(request.getEmail());
        kcUser.setEmail(request.getEmail());
        kcUser.setEnabled(true);


        try {
            UserResource userResource = keycloak.realm(USER_REALM).users().get(request.getKeycloakId());
            UserRepresentation user = userResource.toRepresentation();
            keycloak.realm(USER_REALM)
                    .users()
                    .get(request.getKeycloakId())
                    .update(kcUser);
        } catch (BadRequestException e) {
            log.error("Keycloak update failed: " + e.getMessage());
            throw e;
        }

        log.info("Keycloak user updated successfully: {}", request.getKeycloakId());
    }
}
