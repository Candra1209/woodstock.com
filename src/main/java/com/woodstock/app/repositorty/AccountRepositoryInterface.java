package com.woodstock.app.repositorty;

import com.woodstock.app.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepositoryInterface extends JpaRepository<Account, UUID> , JpaSpecificationExecutor<Account> {
    Optional<Account> findByUsername(String username);

    Boolean existsByUsername(String username);
}
