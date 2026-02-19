package com.woodstock.app.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String cekApp(Authentication authentication){

        return "OK = " + authentication.getName() + " = " + authentication.getAuthorities() ;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String cekAppAdmin(Authentication authentication){

        return "OK = " + authentication.getName() + " = " + authentication.getAuthorities() ;
    }
}
