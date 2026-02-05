package com.woodstock.app.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@Getter
public class ErrorResponse {

    private Integer status;
    private String message;
    private String url;

}
