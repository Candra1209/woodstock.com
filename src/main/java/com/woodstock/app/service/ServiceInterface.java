package com.woodstock.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ServiceInterface <T, R>{

    R create(T t);
    R getById(UUID id);
    Page<R> getAll(Pageable pageable);
    R deleteById(UUID id);
    R update(T t, UUID id);

}
