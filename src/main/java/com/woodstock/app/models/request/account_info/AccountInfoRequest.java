package com.woodstock.app.models.request.account_info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfoRequest {

    private String fullname;
    private String email;
    private String contact;
    private String job;

}
