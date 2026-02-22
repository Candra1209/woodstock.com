package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.RoleEnum;
import com.woodstock.app.entity.Roles;
import com.woodstock.app.repositorty.RolesRepositoryInterface;
import com.woodstock.app.utils.exception.global.DataNotFoundException;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RoleServiceImpl extends BaseServiceImpl<RolesRepositoryInterface, Roles>{

    @Autowired
    protected RoleServiceImpl(RolesRepositoryInterface repository, EntityManager entityManager) {
        super(repository, entityManager);
    }

    public Roles findByName(RoleEnum roleEnum){

        log.info("Get role by name");
        return repository.findByName(roleEnum)
                .orElseThrow(
                        () -> new DataNotFoundException("cannot found role by name " + roleEnum.toString())
                );

    }

    public Optional<Roles> findByNameOptional(RoleEnum roleEnum){

        log.info("Get role by name return optional");
        return repository.findByName(roleEnum);

    }

}
