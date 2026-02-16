package com.woodstock.app.entity;

import com.woodstock.app.models.response.roles.RolesResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Roles {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;


    public RolesResponse toResponse(){
        return RolesResponse.builder()
                .id(id)
                .name(role.toString())
                .build();
    }
}
