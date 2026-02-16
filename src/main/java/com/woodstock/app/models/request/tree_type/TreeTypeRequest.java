package com.woodstock.app.models.request.tree_type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class TreeTypeRequest {

    private UUID id;
    private String name;
    private String typeCode;

}
