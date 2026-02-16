package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.Roles;
import com.woodstock.app.repositorty.RolesRepositoryInterface;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RolesRepositoryInterface, Roles>{

    @Autowired
    protected RoleServiceImpl(RolesRepositoryInterface repository, EntityManager entityManager) {
        super(repository, entityManager);
    }
}
