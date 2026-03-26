package com.woodstock.app.entity.mongo_entity;

import com.woodstock.app.entity.TreeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "tree_collection")
public class Tree {

    @Id
    private String id;

    private Double length;

    @Field(value = "bottom_diameter")
    private Double bottomDiameter;

    @Field(value = "top_diameter")
    private Double topDiameter;

    @Field(value = "avg_diameter")
    private Double avgDiameter;

    private Double volume;

    @Field(value = "location_id")
    private String  locationId;

    @Field(value = "tree_type_id")
    private String  treeTypeId;

    @Field(value = "scaler_id")
    private String  scalerId;

    @Field(value = "faller_id")
    private String  fallerId;

    @Field(value = "is_deleted")
    private boolean isDeleted = false;

}
