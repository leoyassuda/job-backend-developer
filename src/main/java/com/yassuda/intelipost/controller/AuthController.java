package com.yassuda.intelipost.controller;

import com.yassuda.intelipost.exception.ApplicationException;
import com.yassuda.intelipost.model.User;
import com.yassuda.intelipost.payload.ApiResponse;
import com.yassuda.intelipost.payload.JwtAuthenticationResponse;
import com.yassuda.intelipost.payload.LoginRequest;
import com.yassuda.intelipost.payload.SignUpRequest;
import com.yassuda.intelipost.service.AuthService;
import com.yassuda.intelipost.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            CompletableFuture<String> token = authService.authUser(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(new JwtAuthenticationResponse(token.get()));
        } catch (UsernameNotFoundException | InterruptedException | ExecutionException e) {
            logger.error("Error to authenticate a user: " + loginRequest.getUsernameOrEmail());
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getCause().getMessage()));
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.checkExistsUserName(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userService.checkExistsEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        User result = userService.signup(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}