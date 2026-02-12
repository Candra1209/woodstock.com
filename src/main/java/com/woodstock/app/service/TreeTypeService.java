package com.woodstock.app.service;

import com.woodstock.app.entity.TreeType;
import com.woodstock.app.models.request.TreeTypeRequest;
import com.woodstock.app.models.response.tree_type.TreeTypeResponse;
import com.woodstock.app.repositorty.TreeTypeRepositoryInterface;
import com.woodstock.app.service.interfaces.ServiceInterface;
import com.woodstock.app.utils.exception.CannotFoundTreeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TreeTypeService implements ServiceInterface<TreeTypeRequest, TreeTypeResponse> {

    @Autowired
    private TreeTypeRepositoryInterface repository;


    @Override
    public TreeTypeResponse create(TreeTypeRequest request) {
        TreeType newType = TreeType.builder()
                .name(request.getName())
                .typeCode(request.getTypeCode())
                .build();

        return repository.save(newType).toResponse();
    }

    @Override
    public TreeTypeResponse getById(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new CannotFoundTreeType("there no tree type with ID : " + id)
        ).toResponse();
    }

    @Override
    public Page<TreeTypeResponse> getAll(Pageable pageable) {

       return repository.findAll(pageable).map(TreeType::toResponse);
    }

    public Page<TreeTypeResponse> getAllWithDeleted(Pageable pageable) {

        return repository.findAllwithDeleted(pageable).map(TreeType::toResponse);
    }

    public void recover(UUID id){

        repository.recover(id);

    }

    public Page<TreeTypeResponse> getAllDeleted( Pageable pageable){
        return repository.getAllDeleted(pageable).map(TreeType::toResponse);
    }

    @Override
    public TreeTypeResponse deleteById(UUID id) {
        TreeType target = repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("there no tree type with ID : " + id)
                );
        repository.delete(target);

        return target.toResponse();
    }

    @Override
    public TreeTypeResponse update(TreeTypeRequest request, UUID id) {
        TreeType target = repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("there no tree type with ID : " + id)
                );

        TreeType updeted = TreeType.builder()
                .id(target.getId())
                .name(request.getName())
                .typeCode(request.getTypeCode())
                .build();

        return repository.save(updeted).toResponse();

    }
}
