package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.Account;
import com.woodstock.app.repositorty.AccountRepositoryInterface;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends BaseServiceImpl<AccountRepositoryInterface, Account> {

    @Autowired
    protected AccountServiceImpl(AccountRepositoryInterface repository, EntityManager entityManager) {
        super(repository, entityManager);
    }
}
