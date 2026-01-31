package com.woodstock.app.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class TreeTypeRequest {

    private UUID id;
    private String name;

}
