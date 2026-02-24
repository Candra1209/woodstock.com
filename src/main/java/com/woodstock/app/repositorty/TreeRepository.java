package com.woodstock.app.repositorty;

import com.woodstock.app.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TreeRepository extends JpaRepository<Tree, UUID>, JpaSpecificationExecutor<Tree> {
}
