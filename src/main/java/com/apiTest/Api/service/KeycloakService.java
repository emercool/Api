package com.apiTest.Api.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.keycloak.adapters.springsecurity.KeycloakAuthenticationException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.apiTest.Api.model.ApiResponse;
import com.apiTest.Api.model.ResetPasswordRequest;
import com.apiTest.Api.model.SignInRequest;
import com.apiTest.Api.model.SignUpUser;

import jakarta.ws.rs.core.Response;

@Service
public class KeycloakService {

    //private static final Logger logger = LoggerFactory.getLogger(KeycloakService.class);

    private final Keycloak keycloak;
    private final String serverUrl;
    private final String realm;
    private final String clientId;
    private final String clientSecret;

    public KeycloakService(
            @Value("${keycloak.server-url}") String serverUrl,
            @Value("${keycloak.realm}") String realm,
            @Value("${keycloak.client-id}") String clientId,
            @Value("${keycloak.client-secret}") String clientSecret) {
        this.serverUrl = serverUrl;
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;


        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType("client_credentials")
                .build();
    }

    public ApiResponse signUp(SignUpUser signUpUser) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(signUpUser.getUsername());
        user.setEmail(signUpUser.getEmail());
        user.setEnabled(true);

        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(signUpUser.getPassword());
        credentials.setTemporary(false); // Optional: Mark password as non-temporary
        user.setCredentials(Collections.singletonList(credentials));

        Response response = keycloak.realm(realm).users().create(user);
        if (response.getStatus() == 201) {
            return new ApiResponse(true, "User created successfully.");
        } else {
            String errorMsg = String.format("Failed to create user. HTTP Status: %d, Response: %s",
            response.getStatus(), response.readEntity(String.class));
            return new ApiResponse(false, errorMsg);
        }
    }
    
    public ApiResponse resetPasswordByEmail(ResetPasswordRequest resetPasswordRequest) {
        String email = resetPasswordRequest.getEmail();
        String newPassword = resetPasswordRequest.getPassword();  // New password

        try {
            // Step 1: Find the user by email
            String userId = getUserIdByEmail(email);
            if (userId == null) {
                return new ApiResponse(false, "User not found for email: " + email);
            }

            // Step 2: Reset the user's password
            resetUserPassword(userId, newPassword);
            return new ApiResponse(true, "Password for user with email " + email + " has been successfully reset.");

        } catch (Exception e) {
            return new ApiResponse(false, "User not found for email: " + email);
        }
    }

    private boolean authenticateUser(String email, String password) {
       
        try {     

            Keycloak tempKeycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(email) // Authenticate using email
                .password(password)  // Password from request body
                .grantType("password")
                .build();

            // Attempt to get the token to verify authentication
            tempKeycloak.tokenManager().getAccessTokenString();
            return true; // Authentication successful
        } catch (Exception e) {
            return false; // Authentication failed
        }
    }

    // Helper method to reset the password of the user by their userId
    private void resetUserPassword(String userId, String newPassword) {
        try {
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(newPassword); // Set the new password
            credential.setTemporary(false);   // Set it to false to make the password permanent
            
            // Reset the password in Keycloak
            keycloak.realm(realm).users().get(userId).resetPassword(credential);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset password for user with ID " + userId + ": " + e.getMessage(), e);
        }
    }
    
    public ApiResponse signInByEmail(SignInRequest signInRequest) {
        try {
            String email = signInRequest.getEmail();  // Email from request
            String password = signInRequest.getPassword();  // Password from request body
    
            // Check if email and password are provided
            if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
                return new ApiResponse(false, "Email and password must be provided");
            }
    
            // Search for user using email
            String userId = getUserIdByEmail(email);
            if (userId == null) {
                return new ApiResponse(false, "User not found for email: " + email);
            }
    
            // Authenticate the user using the email (email is treated as username)
            Keycloak tempKeycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .username(email)  // Authenticate using email
                    .password(password)  // Password from request body
                    .grantType("password")
                    .build();
    
            // Attempt to get the access token from Keycloak
            //String accessToken = tempKeycloak.tokenManager().getAccessTokenString();
            
            // Return the success response with the access token
            ApiResponse apiResponse = new ApiResponse(true, "Login successful");
            //apiResponse.setToken(accessToken);

            //apiResponse.setToken(accessToken);
            return apiResponse;
    
        } catch (KeycloakAuthenticationException e) {
            // Handle Keycloak authentication issues specifically
            return new ApiResponse(false, "Keycloak authentication failed: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            return new ApiResponse(false, "Login failed due to an unexpected error: " + e.getMessage());
        }
    }
    
    
    private String getUserIdByEmail(String email) {
        try {
            // Ensure you are searching by email field
            List<UserRepresentation> users = keycloak.realm(realm)
                    .users()
                    .search("", 0, 100, true)  // Empty username search with pagination
                    .stream()
                    .filter(user -> user.getEmail() != null && user.getEmail().equals(email))  // Filter by email manually
                    .collect(Collectors.toList());

            if (users.isEmpty()) {
                return null;  // No user found with the given email
            }

            UserRepresentation user = users.get(0);
            return user.getId();  // Return the userId
        } catch (Exception e) {
            throw new RuntimeException("Failed to find user by email: " + e.getMessage(), e);
        }
    }
        
}
