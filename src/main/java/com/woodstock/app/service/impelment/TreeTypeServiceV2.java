package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.TreeType;
import com.woodstock.app.repositorty.TreeTypeRepositoryInterface;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreeTypeServiceV2 extends BaseServiceImpl<TreeTypeRepositoryInterface, TreeType> {

    @Autowired
    protected TreeTypeServiceV2(TreeTypeRepositoryInterface repository, EntityManager entityManager) {
        super(repository, entityManager);
    }
}
