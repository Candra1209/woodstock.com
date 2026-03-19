package com.woodstock.app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "jobs")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Jobs {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private JobsEnum name;

}
