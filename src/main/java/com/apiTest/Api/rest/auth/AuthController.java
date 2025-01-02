package com.apiTest.Api.rest.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiTest.Api.model.ApiResponse;
import com.apiTest.Api.model.ResetPasswordRequest;
import com.apiTest.Api.model.SignInRequest;
import com.apiTest.Api.model.SignUpUser;
import com.apiTest.Api.service.KeycloakService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth")
@Api(tags = "Authentication API", description = "API for user authentication operations")
public class AuthController {

    private final KeycloakService keycloakService;

    public AuthController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping("/signUp")
    @ApiOperation(value = "Sign up a new user", notes = "Creates a new user account")
    public ResponseEntity<ApiResponse> signUp(@RequestBody SignUpUser signUpUser) {
        return ResponseEntity.ok(keycloakService.signUp(signUpUser));
    }

    @PostMapping("/signin")
    @ApiOperation(value = "Sign in a user", notes = "Authenticates an existing user")
    public ResponseEntity<ApiResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(keycloakService.signInByEmail(signInRequest));
    }

    @PostMapping("/resetpassword")
    @ApiOperation(value = "Reset user password", notes = "Resets the password for an existing user")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.ok(keycloakService.resetPasswordByEmail(resetPasswordRequest));
    }
}
