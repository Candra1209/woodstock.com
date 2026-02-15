package com.woodstock.app.models.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortParams {

    private String filter = "id";
    private String order = "asc";

}
