package com.woodstock.app.repositorty.mongo_repository;

import com.woodstock.app.entity.mongo_entity.Tree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TreeRepositoryMongo extends MongoRepository<Tree, String> {

    List<Tree> findAllByIsDeletedFalse();
    Page<Tree> findAllByIsDeletedFalse(Pageable pageable);
    Optional<Tree> findByIdAndIsDeletedFalse(String id);

}
