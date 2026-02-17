package com.woodstock.app.security.service;

import com.woodstock.app.entity.Account;
import com.woodstock.app.service.impelment.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final AccountServiceImpl accountService;

    @Autowired
    public CustomUserDetailService(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountService.findByUsername(username);

        Set<GrantedAuthority> authorities = account.getRoles().stream()
                .map(
                        roles -> {
                            return new SimpleGrantedAuthority(roles.getName().toString());
                        }
                ).collect(Collectors.toSet());

        return new User(
                account.getUsername(),
                account.getPassword(),
                authorities
        );
    }
}
