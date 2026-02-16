package com.woodstock.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;


    private String username;
    private String password;


    private Set<RoleEnum> roles = new HashSet<>();
}
