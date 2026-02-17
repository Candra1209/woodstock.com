package com.woodstock.app.config;

import com.woodstock.app.models.response.ErrorResponse;
import com.woodstock.app.utils.exception.CannotFoundTreeType;
import com.woodstock.app.utils.exception.InvalidQueryParameter;
import com.woodstock.app.utils.tool.UrlBuilderHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.UriBuilderFactory;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TreeTypeExceptionHandler {

    @ExceptionHandler(CannotFoundTreeType.class)
    public ResponseEntity<ErrorResponse> handleCannotFoundTreeType(CannotFoundTreeType ex, HttpServletRequest request){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .url(UrlBuilderHelper.getFullUrl(request))
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidQueryParameter.class)
    public ResponseEntity<ErrorResponse> handleInvalidStatusGetAllTreeType(InvalidQueryParameter ex, HttpServletRequest request) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(ex.getMessage())
                            .url(UrlBuilderHelper.getFullUrl(request))
                            .build()
            );

    }

}
