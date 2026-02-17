package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.Account;
import com.woodstock.app.entity.RoleEnum;
import com.woodstock.app.entity.Roles;
import com.woodstock.app.models.response.auth.LoginResponse;
import com.woodstock.app.models.response.auth.RegisterResponse;
import com.woodstock.app.security.jwt.JwtUtils;
import com.woodstock.app.utils.exception.UsernameAlreadyExists;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class AuthService {

    private final JwtUtils jwtUtils;
    private final AccountServiceImpl accountService;
    private final RoleServiceImpl roleService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(JwtUtils jwtUtils, AccountServiceImpl accountService, RoleServiceImpl roleService, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.accountService = accountService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public LoginResponse login(String username, String password) {

        log.info("processing authentication");
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    username, password
            )
        );

        log.info("generate jwt token");
        String accessToken = jwtUtils.generateToken(authentication);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional
    public RegisterResponse registser(String username, String password){


        Roles roleUser = roleService.findByName(RoleEnum.ROLE_USER);

        if (accountService.isUsernameExists(username)){
            throw new UsernameAlreadyExists("username already exists");
        }

        log.info("save new account to database");
        Account newAccount = accountService.save(
                Account.builder()
                        .username(username)
                        .password(password)
                        .roles(Set.of(roleUser))
                        .build()
        );

        return RegisterResponse.builder()
                .username(newAccount.getUsername())
                .CreatedAt(newAccount.getCreateAt())
                .build();
    }
}
