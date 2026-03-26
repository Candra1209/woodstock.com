package com.woodstock.app.models.response.tree;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class TreeMinResponse {

    private UUID id;

    @JsonProperty("scaller_id")
    private UUID scllerId;
    private String scaller;

    @JsonProperty("faller_id")
    private UUID fallerId;
    private String faller;
    private String location;
    private String type;
    private Double length;

    @JsonProperty("bottom_diameter")
    private Double bottomDiameter;

    @JsonProperty("top_diameter")
    private Double topDiameter;

    @JsonProperty("avg_diameter")
    private Double avgDiameter;
    private Double volume;

}
