package com.woodstock.app.service.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BaseEntityService<
        T extends JpaRepository<R, UUID>,
        R > {

    R save(R r);
    List<R> saveAll(List<R> r);
    R findbyId(UUID id);
    List<R> findAll();
    R delete(UUID id);
    void delete(R r);
    R Update(R r);

}
