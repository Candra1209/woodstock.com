package com.woodstock.app.config;

import com.woodstock.app.models.response.ErrorResponse;
import com.woodstock.app.utils.exception.AccountUserNotFound;
import com.woodstock.app.utils.exception.ReEnteredPasswordNotEqual;
import com.woodstock.app.utils.tool.UrlBuilderHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class,
            InvalidDataAccessApiUsageException.class,
            PropertyReferenceException.class,
            ReEnteredPasswordNotEqual.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex, HttpServletRequest request) {

            return ResponseEntity.badRequest().body(
                    ErrorResponse.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .url(UrlBuilderHelper.getFullUrl(request))
                            .message("BAD REQUEST : " + ex.getMessage())
                            .build()
            );
    }

    @ExceptionHandler({
            AccountUserNotFound.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex, HttpServletRequest request) {

        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .url(UrlBuilderHelper.getFullUrl(request))
                        .message("NOT FOUND : " + ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseException( DataAccessException ex){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage() + " from repository")
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
