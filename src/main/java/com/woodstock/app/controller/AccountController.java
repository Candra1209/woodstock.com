package com.woodstock.app.controller;

import com.woodstock.app.models.response.SuccessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @GetMapping
    private String cekApp(){

        return "OK";
    }
}
