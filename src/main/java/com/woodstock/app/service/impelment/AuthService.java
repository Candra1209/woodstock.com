package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.Account;
import com.woodstock.app.entity.AccountInfo;
import com.woodstock.app.entity.RoleEnum;
import com.woodstock.app.entity.Roles;
import com.woodstock.app.models.request.auth.RegisterRequest;
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
    private final AccountInfoServiceImpl accountInfoService;
    private final RoleServiceImpl roleService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(JwtUtils jwtUtils, AccountServiceImpl accountService, AccountInfoServiceImpl accountInfoService, RoleServiceImpl roleService, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.accountService = accountService;
        this.accountInfoService = accountInfoService;
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

        log.info("try get User role from database");
        Roles roleUser = roleService.findByName(RoleEnum.ROLE_USER);

        log.info("checking if username already exists");
        if (accountService.isUsernameExists(username)){
            log.error("username already exists : {}", username);
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

        AccountInfo accountInfo = createDummyAccountInfo(newAccount);

        return RegisterResponse.builder()
                .username(newAccount.getUsername())
                .fullname(accountInfo.getFullname())
                .CreatedAt(newAccount.getCreateAt())
                .build();
    }

    @Transactional
    public RegisterResponse registser(String username, String password, RoleEnum roleEnum){

        log.info("checking if user try create account with role admin : {}", roleEnum.toString());
        if (roleEnum.equals(RoleEnum.ROLE_ADMIN)){
            throw new RuntimeException("you cannot make account with admin role");
        }

        Roles roleUser = roleService.findByName(roleEnum);

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

        AccountInfo accountInfo = createDummyAccountInfo(newAccount);

        return RegisterResponse.builder()
                .username(newAccount.getUsername())
                .fullname(accountInfo.getFullname())
                .CreatedAt(newAccount.getCreateAt())
                .build();
    }


    private AccountInfo createDummyAccountInfo(Account newAccount) {
        log.info("create dummy account info for {}", newAccount.getId());
        AccountInfo accountInfo = AccountInfo.builder()
                .fullname("user-"+ newAccount.getId())
                .account(newAccount)
                .jobs(Set.of())
                .build();

        log.info("save {} as dummy account info ,make sure to update it", accountInfo.getFullname());
        return accountInfoService.save(accountInfo);
    }
}
