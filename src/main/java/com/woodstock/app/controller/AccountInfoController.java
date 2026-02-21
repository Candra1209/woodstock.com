package com.woodstock.app.controller;

import com.woodstock.app.entity.Account;
import com.woodstock.app.models.response.SuccessResponse;
import com.woodstock.app.service.impelment.AccountInfoServiceImpl;
import com.woodstock.app.utils.constants.RouteAppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(RouteAppConstant.BASE_V1 + RouteAppConstant.ACCOUNT_INFO)
public class AccountInfoController {

    private final AccountInfoServiceImpl accountInfoService;

    @Autowired
    public AccountInfoController(AccountInfoServiceImpl accountInfoService) {
        this.accountInfoService = accountInfoService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResponse<?>> getAccountInfoById(@PathVariable String id){

        SuccessResponse<?> result = SuccessResponse.builder()
                .status(HttpStatus.FOUND)
                .message("success find account by id")
                .data(accountInfoService.findbyId(UUID.fromString(id)).toRegisterResponse())
                .build();

        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public ResponseEntity<SuccessResponse<?>> getAccountInfoById(Authentication authentication){



        SuccessResponse<?> result = SuccessResponse.builder()
                .status(HttpStatus.FOUND)
                .message("success find account by id")
                .data(accountInfoService.getAccountInfoByAccountUsername(authentication.getName()).toRegisterResponse())
                .build();

        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }
}
