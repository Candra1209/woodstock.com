package com.woodstock.app.repositorty;

import com.woodstock.app.entity.Account;
import com.woodstock.app.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface AccountInfoRepository extends JpaRepository<AccountInfo, UUID>, JpaSpecificationExecutor<AccountInfo> {

    Optional<AccountInfo> findByAccount(Account account);

}
