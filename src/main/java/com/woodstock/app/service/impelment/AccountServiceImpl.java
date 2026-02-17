package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.Account;
import com.woodstock.app.repositorty.AccountRepositoryInterface;
import com.woodstock.app.utils.exception.AccountUserNotFound;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.stream.Stream;

@Service
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
                        () -> new AccountUserNotFound("there no account with username : " + username )
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
}
