package com.woodstock.app.repositorty;

import com.woodstock.app.entity.Roles;
import com.woodstock.app.entity.TreeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface RolesRepositoryInterface extends JpaRepository<Roles, UUID>, JpaSpecificationExecutor<Roles> {
}
