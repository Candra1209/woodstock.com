package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.Account;
import com.woodstock.app.repositorty.AccountRepositoryInterface;
import com.woodstock.app.utils.exception.account.AccountUserNotFoundException;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl extends BaseServiceImpl<AccountRepositoryInterface, Account> {

    private final PasswordEncoder encoder;

    @Autowired
    protected AccountServiceImpl(AccountRepositoryInterface repository, EntityManager entityManager, PasswordEncoder encoder) {
        super(repository, entityManager);
        this.encoder = encoder;
    }

    public Account findByUsername(String username){
        return repository.findByUsername(username)
                .orElseThrow(
                        () -> new AccountUserNotFoundException("there no account with username : " + username )
                );
    }

    public Optional<Account> findByUsernameOptional(String username){
        return repository.findByUsername(username);
    }

    @Override
    public Account save(Account account) {

        account.setPassword(encoder.encode(account.getPassword()));

        return super.save(account);
    }

    public Boolean isUsernameExists(String username){

        log.info("check if username already exists");
        return repository.existsByUsername(username);

    }
}
