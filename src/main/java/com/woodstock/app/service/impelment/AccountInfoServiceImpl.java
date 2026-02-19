package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.Account;
import com.woodstock.app.entity.AccountInfo;
import com.woodstock.app.entity.BaseEntity;
import com.woodstock.app.models.request.account_info.AccountInfoRequest;
import com.woodstock.app.repositorty.AccountInfoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class AccountInfoServiceImpl extends BaseServiceImpl<AccountInfoRepository, AccountInfo> {

    private final AccountServiceImpl accountService;

    protected AccountInfoServiceImpl(AccountInfoRepository repository, EntityManager entityManager, AccountServiceImpl accountService) {
        super(repository, entityManager);
        this.accountService = accountService;
    }

    public AccountInfo getAccountInfoByAccountUsername(String username){

        Account account = accountService.findByUsername(username);

        return repository.findByAccount(account)
                .orElseThrow(
                        () -> new RuntimeException("there no account info with account id : " + account.getId())
                );
    }

    @Transactional
    public AccountInfo updateAccountInfo(String username, AccountInfoRequest request){

        log.info("try get account from database");
        Account account = accountService.findByUsername(username);

        AccountInfo accountInfo = repository.findByAccount(account)
                .orElseThrow(
                        () -> new RuntimeException("there no account info with account id : " + account.getId())
                );

        log.info("try updating account info");
        accountInfo.setFullname(request.getFullname());
        accountInfo.setContact(request.getContact());
        accountInfo.setEmail(request.getEmail());

        log.info("update account info di database");
        return repository.save(accountInfo);
    }

}
