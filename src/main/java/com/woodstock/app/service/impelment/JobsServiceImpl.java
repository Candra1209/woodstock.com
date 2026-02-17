package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.Jobs;
import com.woodstock.app.entity.JobsEnum;
import com.woodstock.app.entity.RoleEnum;
import com.woodstock.app.entity.Roles;
import com.woodstock.app.repositorty.JobsRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class JobsServiceImpl extends BaseServiceImpl<JobsRepository, Jobs>{

    @Autowired
    protected JobsServiceImpl(JobsRepository repository, EntityManager entityManager) {
        super(repository, entityManager);
    }



    public Optional<Jobs> findByNameOptional(JobsEnum jobsEnum){

        log.info("Get job by it name");
        return repository.findByName(jobsEnum);

    }


}
