package com.woodstock.app.config.exception_handler;

import com.woodstock.app.entity.Jobs;
import com.woodstock.app.entity.JobsEnum;
import com.woodstock.app.entity.RoleEnum;
import com.woodstock.app.entity.Roles;
import com.woodstock.app.service.impelment.JobsServiceImpl;
import com.woodstock.app.service.impelment.RoleServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
public class InitializeOriginalData {

    private final RoleServiceImpl roleService;
    private final JobsServiceImpl jobsService;

    @Autowired
    public InitializeOriginalData(RoleServiceImpl roleService, JobsServiceImpl jobsService) {
        this.roleService = roleService;
        this.jobsService = jobsService;
    }

    @PostConstruct
    public void init(){

    createRoleIfNotExists(RoleEnum.ROLE_ADMIN);
    createRoleIfNotExists(RoleEnum.ROLE_USER);
    createRoleIfNotExists(RoleEnum.ROLE_GUEST);

    createJobIfNotExists(JobsEnum.SCALLER);
    createJobIfNotExists(JobsEnum.FALLER);
    createJobIfNotExists(JobsEnum.BUCKER);
    createJobIfNotExists(JobsEnum.OPERATOR);

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
