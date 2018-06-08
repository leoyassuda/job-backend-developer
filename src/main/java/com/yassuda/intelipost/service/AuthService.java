package com.yassuda.intelipost.service;

import com.yassuda.intelipost.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * Autenticador de um usuário por 'username' ou 'e-mail' e seu 'password.
     *
     * @param usernameOrEmail o username ou e-mail do usuário.
     * @param password        a senha do usuário.
     * @return uma {@link String} com o token de autenticação.
     */
    @Async
    public CompletableFuture<String> authUser(String usernameOrEmail, String password) {

        logger.info("Looking up and authenticate a user: " + usernameOrEmail);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usernameOrEmail, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        logger.info("User " + usernameOrEmail + " is authenticated");
        return CompletableFuture.completedFuture(jwt);
    }
}
