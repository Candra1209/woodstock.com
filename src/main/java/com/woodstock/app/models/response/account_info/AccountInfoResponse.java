package com.woodstock.app.models.response.account_info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfoResponse {
    private UUID id;
    private String username;
    private String fullname;
    private String contact;
    private String email;
    private List<String> jobs;
    private List<String> roles;
}
