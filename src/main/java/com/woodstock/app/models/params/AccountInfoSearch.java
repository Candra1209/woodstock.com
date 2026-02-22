package com.woodstock.app.models.params;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class AccountInfoSearch {

    private String fullname;
    private String contact;
    private String email;
    private String role;
    private List<String> jobs;

}
