package com.woodstock.app.entity;

import com.woodstock.app.models.response.roles.RolesResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Roles {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleEnum name;


    public RolesResponse toResponse(){
        return RolesResponse.builder()
                .id(id)
                .name(name.toString())
                .build();
    }
}
