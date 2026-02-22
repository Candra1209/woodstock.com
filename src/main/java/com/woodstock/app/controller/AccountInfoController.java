package com.woodstock.app.controller;
import com.woodstock.app.entity.Jobs;
import com.woodstock.app.models.request.account_info.AccountInfoRequest;
import com.woodstock.app.models.request.jobs.JobsRequest;
import com.woodstock.app.models.response.SuccessResponse;
import com.woodstock.app.models.response.account_info.AccountInfoResponse;
import com.woodstock.app.models.response.auth.RegisterResponse;
import com.woodstock.app.service.impelment.AccountInfoServiceImpl;
import com.woodstock.app.utils.constants.RouteAppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<SuccessResponse<RegisterResponse>> getAccountInfoById(@PathVariable String id){

        SuccessResponse<RegisterResponse> result = SuccessResponse.<RegisterResponse>builder()
                .status(HttpStatus.FOUND)
                .message("success find account by id")
                .data(accountInfoService.findbyId(UUID.fromString(id)).toRegisterResponse())
                .build();

        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public ResponseEntity<SuccessResponse<RegisterResponse>> getAccountInfoById(Authentication authentication){

        SuccessResponse<RegisterResponse> result = SuccessResponse.<RegisterResponse>builder()
                .status(HttpStatus.FOUND)
                .message("success find account by id")
                .data(accountInfoService.getAccountInfoByAccountUsername(authentication.getName()).toRegisterResponse())
                .build();

        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }

    @PutMapping("/me/update")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<SuccessResponse<AccountInfoResponse>> updateOwnInfo(@RequestBody AccountInfoRequest request, Authentication authentication){

        SuccessResponse<AccountInfoResponse> result = SuccessResponse.<AccountInfoResponse>builder()
                .status(HttpStatus.CREATED)
                .message("your account info successfully updated")
                .data(accountInfoService.updateOwnInfo(request, authentication.getName()).toResponse())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResponse<AccountInfoResponse>> updateInfobyAdmin(@PathVariable String id, @RequestBody AccountInfoRequest request){

        SuccessResponse<AccountInfoResponse> result = SuccessResponse.<AccountInfoResponse>builder()
                .status(HttpStatus.CREATED)
                .message("success updated account info for user id : " + id)
                .data(accountInfoService.updateUserInfo(request, UUID.fromString(id)).toResponse())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResponse<AccountInfoResponse>> deleteAccountByAdmin(@PathVariable String id){

        SuccessResponse<AccountInfoResponse> result = SuccessResponse.<AccountInfoResponse>builder()
                .status(HttpStatus.OK)
                .message("success deleted account info with info id : " + id)
                .data(accountInfoService.deleteAccountAndInfo(UUID.fromString(id)).toResponse())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/me/assign")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<SuccessResponse<AccountInfoResponse>> assignJobYourself(@RequestBody JobsRequest request, Authentication authentication){

        SuccessResponse<AccountInfoResponse> result = SuccessResponse.<AccountInfoResponse>builder()
                .status(HttpStatus.CREATED)
                .message("success assign new job to account info with info id : " + authentication.getName())
                .data(accountInfoService.assignWonJob(authentication.getName(), request.getJob()).toResponse())
                .build();

        return ResponseEntity.ok(result);
    }
}
