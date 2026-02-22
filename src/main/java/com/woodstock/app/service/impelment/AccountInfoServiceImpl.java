package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.*;
import com.woodstock.app.models.request.account_info.AccountInfoRequest;
import com.woodstock.app.repositorty.AccountInfoRepository;
import com.woodstock.app.utils.exception.account.AccountUserNotFoundException;
import com.woodstock.app.utils.exception.jobs.JobsAlreadyAssignException;
import com.woodstock.app.utils.exception.jobs.JobsNotFoundException;
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
    private final JobsServiceImpl jobsService;

    protected AccountInfoServiceImpl(AccountInfoRepository repository, EntityManager entityManager, AccountServiceImpl accountService, JobsServiceImpl jobsService) {
        super(repository, entityManager);
        this.accountService = accountService;
        this.jobsService = jobsService;
    }

    public AccountInfo getAccountInfoByAccountUsername(String username){

        Account account = accountService.findByUsername(username);

        return repository.findByAccount(account)
                .orElseThrow(
                        () -> new AccountUserNotFoundException("there no account info with account id : " + account.getId())
                );
    }

    @Transactional
    public AccountInfo updateAccountInfo(String username, AccountInfoRequest request){

        log.info("try get account from database");
        Account account = accountService.findByUsername(username);

        AccountInfo accountInfo = repository.findByAccount(account)
                .orElseThrow(
                        () -> new AccountUserNotFoundException("there no account info with account id : " + account.getId())
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
                            return new AccountUserNotFoundException("there no account info with account  : " + account.getUsername());
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

    @Transactional
    public AccountInfo deleteAccountAndInfo(UUID id){

        AccountInfo accountInfo = findbyId(id);
        Account account = accountInfo.getAccount();

        log.info("try deleting account and account info");
        delete(accountInfo);
        accountService.delete(account);

        return accountInfo;

    }

    public AccountInfo assignWonJob(String username, String job){

        log.info("checking if job input by user included in enum");
        JobsEnum jobTarget = JobsEnum.valueOf(job.toUpperCase());

        log.info("finding job enum in database");
        Jobs newJob = jobsService.findByNameOptional(jobTarget)
                .orElseThrow(
                        () -> new JobsNotFoundException("there no job with type : " + jobTarget.toString())
                );

        log.info("finding account info in database");
        AccountInfo accountInfo = getAccountInfoByAccountUsername(username);

        log.info("checking if jobs already assign to {}", accountInfo.getAccount().getUsername());
        if (accountInfo.getJobs().contains(newJob)){
            log.info("job {} already assign to {}",newJob.getName().toString() , accountInfo.getAccount().getUsername());
            throw new JobsAlreadyAssignException("you already assign as " + newJob.getName().toString());
        }

        log.info("assigning {} job to {} account info", newJob.getName().toString(), accountInfo.getAccount().getUsername());
        accountInfo.getJobs().add(newJob);

        return save(accountInfo);

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
