package com.woodstock.app.entity;

import com.woodstock.app.models.response.auth.RegisterResponse;
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
@Table(name = "account_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfo {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String fullname;

    private String email;

    private String contact;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_jobs",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    private Set<Jobs> jobs = new HashSet<>();

    public RegisterResponse toRegisterResponse(){

        return RegisterResponse.builder()
                .username(account.getUsername())
                .fullname(fullname)
                .contact(contact)
                .email(email)
                .jobs(jobs.stream().map(job -> job.getName().toString()).toList())
                .build();

    }


}
