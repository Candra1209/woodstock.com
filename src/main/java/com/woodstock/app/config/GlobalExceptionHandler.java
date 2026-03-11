package com.woodstock.app.config;

import com.woodstock.app.models.response.ErrorResponse;
import com.woodstock.app.utils.exception.account.AccountUserNotFoundException;
import com.woodstock.app.utils.exception.global.DataNotFoundException;
import com.woodstock.app.utils.exception.jobs.ForbidenJobRoleAccessException;
import com.woodstock.app.utils.exception.jobs.JobsAlreadyAssignException;
import com.woodstock.app.utils.exception.jobs.JobsNotFoundException;
import com.woodstock.app.utils.exception.auth.ReEnteredPasswordNotEqualException;
import com.woodstock.app.utils.exception.auth.UsernameAlreadyExistsException;
import com.woodstock.app.utils.tool.UrlBuilderHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class,
            InvalidDataAccessApiUsageException.class,
            PropertyReferenceException.class,
            ReEnteredPasswordNotEqualException.class})
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
            AccountUserNotFoundException.class,
            JobsNotFoundException.class,
            DataNotFoundException.class

    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .url(UrlBuilderHelper.getFullUrl(request))
                        .message("NOT FOUND : " + ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler({
            UsernameAlreadyExistsException.class,
            JobsAlreadyAssignException.class
    })
    public ResponseEntity<ErrorResponse> handleConflictException(Exception ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ErrorResponse.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .url(UrlBuilderHelper.getFullUrl(request))
                        .message("CONFLICT : " + ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler({
            AuthorizationDeniedException.class,
            ForbidenJobRoleAccessException.class
    })
    public ResponseEntity<ErrorResponse> handleForbiden(Exception ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ErrorResponse.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .url(UrlBuilderHelper.getFullUrl(request))
                        .message("CONFLICT : " + ex.getMessage())
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
