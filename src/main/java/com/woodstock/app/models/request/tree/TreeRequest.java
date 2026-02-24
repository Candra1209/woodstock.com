package com.woodstock.app.models.request.tree;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class TreeRequest {

    private String scaller_id;
    private String faller_id;
    private String tree_type_id;
    private String location_id;
    private Double length;
    private Double bottom_diameter;
    private Double top_diameter;

}
