package com.woodstock.app.service;

import com.woodstock.app.entity.TreeType;
import com.woodstock.app.models.request.TreeTypeRequest;
import com.woodstock.app.models.response.TreeTypeResponse;
import com.woodstock.app.repositorty.TreeTypeRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TreeTypeService implements ServiceInterface<TreeTypeRequest, TreeTypeResponse> {

    @Autowired
    private TreeTypeRepositoryInterface repository;


    @Override
    public TreeTypeResponse create(TreeTypeRequest request) {
        TreeType newType = TreeType.builder()
                .name(request.getName()).
                build();

        return repository.save(newType).toResponse();
    }

    @Override
    public TreeTypeResponse getById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("there no tree type with ID : " + id)
        ).toResponse();
    }

    @Override
    public TreeTypeResponse deleteById(UUID id) {
        return null;
    }

    @Override
    public TreeTypeResponse update(TreeTypeRequest request, UUID id) {
        return null;
    }
}
