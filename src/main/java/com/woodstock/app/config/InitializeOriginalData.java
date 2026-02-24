package com.woodstock.app.config;

import com.woodstock.app.entity.*;
import com.woodstock.app.service.impelment.*;
import com.woodstock.app.utils.exception.jobs.JobsNotFoundException;
import com.woodstock.app.utils.seeder.AccountDataSeeder;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InitializeOriginalData {

    private final RoleServiceImpl roleService;
    private final JobsServiceImpl jobsService;
    private final AccountServiceImpl accountService;
    private final AccountInfoServiceImpl accountInfoService;

    private final AccountDataSeeder accountDataSeeder;

    @Autowired
    public InitializeOriginalData(RoleServiceImpl roleService, JobsServiceImpl jobsService, AccountServiceImpl accountService, AccountInfoServiceImpl accountInfoService, AccountDataSeeder accountDataSeeder) {
        this.roleService = roleService;
        this.jobsService = jobsService;
        this.accountService = accountService;
        this.accountInfoService = accountInfoService;
        this.accountDataSeeder = accountDataSeeder;
    }

    @PostConstruct
    public void init(){

        Roles adminRole = createRoleIfNotExists(RoleEnum.ROLE_ADMIN);
        Roles userRole = createRoleIfNotExists(RoleEnum.ROLE_USER);
        createRoleIfNotExists(RoleEnum.ROLE_GUEST);

        createJobIfNotExists(JobsEnum.SCALLER);
        createJobIfNotExists(JobsEnum.FALLER);
        createJobIfNotExists(JobsEnum.BUCKER);
        createJobIfNotExists(JobsEnum.OPERATOR);
        createJobIfNotExists(JobsEnum.GANISPH);
        createJobIfNotExists(JobsEnum.MANAGER_CAMP);
        createJobIfNotExists(JobsEnum.MANAGER_LOGPOND);

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

        createAccountIfNotExist(userRole);


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

    public void createAccountIfNotExist(Roles roles){

        List<AccountDataSeeder.AccountSeeder> list = accountDataSeeder.getSeed();

        list.forEach(accountSeeder -> {

            if (accountService.isUsernameExists(accountSeeder.username)){
                return;
            }

            Account account = Account.builder()
                    .username(accountSeeder.username)
                    .password(accountSeeder.password)
                    .roles(Set.of(roles))
                    .build();

            Account accAccepted = accountService.save(account);

            Set<Jobs> jobs = new HashSet<>();

            accountSeeder.jobs.forEach(s -> {
                jobs.add(
                        jobsService.findByNameOptional(s)
                                .orElseThrow(
                                        () -> new JobsNotFoundException("there no jobs with name : " + s)
                                )
                );
            });

            AccountInfo accountInfo = AccountInfo.builder()
                    .fullname(accountSeeder.fullname)
                    .email(accountSeeder.email)
                    .contact(accountSeeder.contact)
                    .account(account)
                    .jobs(jobs)
                    .build();

            accountInfoService.save(accountInfo);

        });

    };

}
