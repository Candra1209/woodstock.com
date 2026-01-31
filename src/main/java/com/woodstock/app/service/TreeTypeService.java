package com.woodstock.app.service;

import com.woodstock.app.entity.TreeType;
import com.woodstock.app.repositorty.TreeTypeRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TreeTypeService {

    @Autowired
    private TreeTypeRepositoryInterface repository;

    public TreeType create(TreeType type){
        return repository.save(type);
    }

    public TreeType getById(UUID id){
        return repository.findById(id)
                .orElseThrow(
                        () ->  new RuntimeException("there no tree type with ID : " + id)
                );
    }

}
