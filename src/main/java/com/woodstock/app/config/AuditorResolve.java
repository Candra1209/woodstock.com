package com.woodstock.app.config;

import com.woodstock.app.entity.Account;
import com.woodstock.app.service.impelment.AccountServiceImpl;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Security;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuditorResolve implements AuditorAware<Account> {

    private final EntityManager entityManager;
    private final AccountServiceImpl accountService;


    @Override
    public Optional<Account> getCurrentAuditor() {

        //ambil autentication

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) return Optional.empty();

        String username = authentication.getName();

        Account account = accountService.findByUsername(username);

        //cari username di database

        return Optional.ofNullable(account);
    }
}
