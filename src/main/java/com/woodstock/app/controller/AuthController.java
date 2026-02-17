package com.woodstock.app.controller;

import com.woodstock.app.utils.constants.RouteAppConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1"+ RouteAppConstant.AUTH)
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<?> register() {
        return ResponseEntity.ok("Register Route");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok("Login Route");
    }
}
