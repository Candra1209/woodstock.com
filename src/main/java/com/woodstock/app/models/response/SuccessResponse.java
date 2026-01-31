package com.woodstock.app.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SuccessResponse<T> {

    private HttpStatus status;
    private  String message;
    private T data;



}
