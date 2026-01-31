package com.woodstock.app.repositorty;

import com.woodstock.app.entity.TreeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TreeTypeRepositoryInterface extends JpaRepository<TreeType, UUID> {
}
