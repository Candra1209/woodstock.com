package com.woodstock.app.service.impelment;

import com.woodstock.app.models.response.auth.LoginResponse;
import com.woodstock.app.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    private final JwtUtils jwtUtils;
    private final AccountServiceImpl accountService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(JwtUtils jwtUtils, AccountServiceImpl accountService, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse login(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    username, password
            )
        );

        String accessToken = jwtUtils.generateToken(authentication);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
