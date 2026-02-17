package com.woodstock.app.controller;

import com.woodstock.app.models.request.auth.LoginRequest;
import com.woodstock.app.models.request.auth.RegisterRequest;
import com.woodstock.app.models.response.SuccessResponse;
import com.woodstock.app.service.impelment.AuthService;
import com.woodstock.app.utils.constants.RouteAppConstant;
import com.woodstock.app.utils.exception.ReEnteredPasswordNotEqual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1"+ RouteAppConstant.AUTH)
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<?>> register(@RequestBody RegisterRequest request) {

        if (!request.getPassword().equals(request.getPassword_repeat())){
            throw new ReEnteredPasswordNotEqual("password and repeated-password not same");
        }

        SuccessResponse<?> result = SuccessResponse.builder()
                .status(HttpStatus.OK)
                .message("register successfully")
                .data(authService.registser(request.getUsername(), request.getPassword_repeat()))
                .build();

        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<?>> login(@RequestBody LoginRequest request) {

        SuccessResponse<?> result = SuccessResponse.builder()
                .status(HttpStatus.OK)
                .message("register successfully")
                .data(authService.login(request.getUsername(), request.getPassword()))
                .build();

        return ResponseEntity.ok(result);
    }
}
