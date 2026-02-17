package com.woodstock.app.config;

import com.woodstock.app.entity.*;
import com.woodstock.app.service.impelment.AccountServiceImpl;
import com.woodstock.app.service.impelment.JobsServiceImpl;
import com.woodstock.app.service.impelment.RoleServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Component
public class InitializeOriginalData {

    private final RoleServiceImpl roleService;
    private final JobsServiceImpl jobsService;
    private final AccountServiceImpl accountService;

    @Autowired
    public InitializeOriginalData(RoleServiceImpl roleService, JobsServiceImpl jobsService, AccountServiceImpl accountService) {
        this.roleService = roleService;
        this.jobsService = jobsService;
        this.accountService = accountService;
    }

    @PostConstruct
    public void init(){

    Roles adminRole = createRoleIfNotExists(RoleEnum.ROLE_ADMIN);
    createRoleIfNotExists(RoleEnum.ROLE_USER);
    createRoleIfNotExists(RoleEnum.ROLE_GUEST);

    createJobIfNotExists(JobsEnum.SCALLER);
    createJobIfNotExists(JobsEnum.FALLER);
    createJobIfNotExists(JobsEnum.BUCKER);
    createJobIfNotExists(JobsEnum.OPERATOR);

    accountService.findByUsernameOptional("admin")
            .orElseGet(
                () -> {

                    Account newAccount = new Account();
                    newAccount.setUsername("admin");
                    newAccount.setPassword("admin");
                    newAccount.setRoles(Set.of(adminRole));

                    return accountService.save(newAccount);
                }
            );

    }

    public Roles createRoleIfNotExists(RoleEnum roleEnum){

        return roleService.findByNameOptional(roleEnum)
                .orElseGet(() -> {

                    Roles newRole = Roles.builder()
                            .name(roleEnum)
                            .build();

                    return roleService.save(newRole);
                });

    }

    public Jobs createJobIfNotExists(JobsEnum jobsEnum){
        return jobsService.findByNameOptional(jobsEnum)
                .orElseGet(
                        () -> {

                            Jobs newJob = Jobs.builder()
                                    .name(jobsEnum)
                                    .build();

                            return jobsService.save(newJob);
                        }
                );
    }

}
