package com.woodstock.app.models.response.tree_type;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TreeTypeResponse implements Serializable {

    private UUID id;
    private String name;
    private String typeCode;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isDeleted;

}
