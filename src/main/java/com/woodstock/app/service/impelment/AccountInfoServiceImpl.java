package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.Account;
import com.woodstock.app.entity.AccountInfo;
import com.woodstock.app.models.request.account_info.AccountInfoRequest;
import com.woodstock.app.repositorty.AccountInfoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;


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

    @Transactional
    public AccountInfo updateOwnInfo(AccountInfoRequest request, String username){

        log.info("try find account with username {}", username);
        Account account = accountService.findByUsername(username);

        log.info("try find account information with username {}", username);
        AccountInfo accountInfo = repository.findByAccount(account)
                .orElseThrow(
                        () -> {
                            log.error("there no account information with account : {}", account.getUsername());
                            return new RuntimeException("there no account info with account  : " + account.getUsername());
                        }
                );

        return updatedAndGetAccountInfo(accountInfo, request);

    }

    @Transactional
    public AccountInfo updateUserInfo(AccountInfoRequest request, UUID id){

        log.info("try find account with id {}", id.toString());
        AccountInfo account = findbyId(id);

        return updatedAndGetAccountInfo(account, request);

    }

    private @NonNull AccountInfo updatedAndGetAccountInfo(AccountInfo accountInfo, AccountInfoRequest request) {
        AccountInfo updatedAccountinfo = AccountInfo.builder()
                .id(accountInfo.getId())
                .fullname(request.getFullname())
                .email(request.getEmail())
                .contact(request.getContact())
                .account(accountInfo.getAccount())
                .jobs(accountInfo.getJobs())
                .build();

        log.info("account {} had been updated", accountInfo.getAccount().getUsername());
        return save(updatedAccountinfo);
    }



}
