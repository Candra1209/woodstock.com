package com.woodstock.app.repositorty;

import com.woodstock.app.entity.Jobs;
import com.woodstock.app.entity.JobsEnum;
import com.woodstock.app.entity.RoleEnum;
import com.woodstock.app.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface JobsRepository extends JpaRepository<Jobs, UUID>, JpaSpecificationExecutor<Jobs> {
    Optional<Jobs> findByName(JobsEnum jobsEnum);
}
