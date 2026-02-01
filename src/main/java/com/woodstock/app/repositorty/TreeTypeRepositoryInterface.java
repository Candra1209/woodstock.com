package com.woodstock.app.repositorty;

import com.woodstock.app.entity.TreeType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TreeTypeRepositoryInterface extends JpaRepository<TreeType, UUID> {

    @Query(value = "SELECT * FROM tree_type", nativeQuery = true)
    public Page<TreeType> findAllwithDeleted(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tree_type SET deleted = 'f' WHERE  id = :id", nativeQuery = true)
    public void recover(UUID id);

    @Query(value = "SELECT * FROM tree_type WHERE deleted = 't'", nativeQuery = true)
    public Page<TreeType> getAllDeleted(Pageable pageable);
}
