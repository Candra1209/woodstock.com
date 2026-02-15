package com.woodstock.app.models.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TreeTypeSearch {
    private String name;
    private String code;

//    @JsonProperty("include_deleted")
//    private Boolean include_deleted;
}
