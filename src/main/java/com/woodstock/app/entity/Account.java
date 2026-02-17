package com.woodstock.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;


    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "account_jobs",
//            joinColumns = @JoinColumn(name = "account_id"),
//            inverseJoinColumns = @JoinColumn(name = "job_id"))
//    private Set<Jobs> jobs = new HashSet<>();
}
