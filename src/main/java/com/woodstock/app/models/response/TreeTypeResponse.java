package com.woodstock.app.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TreeTypeResponse {

    private UUID id;
    private String name;
    private String typeCode;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
