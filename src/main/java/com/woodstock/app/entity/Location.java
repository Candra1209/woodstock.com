package com.woodstock.app.entity;

import com.woodstock.app.models.response.location.LocationResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "location")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location extends BaseEntity {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String latitude;
    private String longitude;

    public LocationResponse toResponse(){
        return LocationResponse.builder()
                .id(id)
                .name(name)
                .longitude(longitude)
                .latitude(latitude)
                .createdAt(getCreateAt())
                .updatedAt(getUpdateAt())
                .build();
    }

}
