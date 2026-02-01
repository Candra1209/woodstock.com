package com.woodstock.app.service;

import java.util.UUID;

public interface ServiceInterface <T, R>{

    R create(T t);
    R getById(UUID id);

    R deleteById(UUID id);
    R update(T t, UUID id);

}
