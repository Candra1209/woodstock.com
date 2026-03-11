package com.woodstock.app.models.response.tree;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woodstock.app.models.response.account_info.AccountInfoResponse;
import com.woodstock.app.models.response.location.LocationResponse;
import com.woodstock.app.models.response.tree_type.TreeTypeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class TreeResponse {

    private UUID id;

    @JsonProperty("scaller")
    private AccountInfoResponse scaller;

    @JsonProperty("faller")
    private  AccountInfoResponse faller;


    private LocationResponse location;

    private TreeTypeResponse type;


    private Double length;

    @JsonProperty("bottom_diameter")
    private Double bottomDiameter;

    @JsonProperty("top_diameter")
    private Double topDiameter;

    @JsonProperty("avg_diameter")
    private Double avgDiameter;
    private Double volume;

}
