package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.TreeType;
import com.woodstock.app.service.interfaces.BaseEntityService;
import com.woodstock.app.utils.exception.CannotFoundTreeType;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class BaseServiceImpl<
        T extends JpaRepository<R , UUID> & JpaSpecificationExecutor<R>,
        R > implements BaseEntityService<T, R> {

    private final T repository;
    private final EntityManager entityManager;


    protected BaseServiceImpl(T repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public R save(R r) {
        return repository.save(r);
    }

    @Override
    public List<R> saveAll(List<R> r) {
        return repository.saveAll(r);
    }

    @Override
    public R findbyId(UUID id) {

        log.info("try find record by id");
        return repository.findById(id)
                .orElseThrow(
                        () -> new CannotFoundTreeType("there no tree type with required id : " + id)
                );
    }

    @Override
    public List<R> findAll() {
        return repository.findAll();
    }

    @Override
    public R delete(UUID id) {
        log.info("delete record by id");
        R r = findbyId(id);
        delete(r);

        return r;
    }

    @Override
    public void delete(R r) {

        repository.delete(r);
        log.info("record deleted!");
    }

    @Override
    public R Update(R r) {
        return repository.save(r);
    }

    @Override
    public Page<R> getPagging(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<R> getPagging(Pageable pageable, Specification<R> specification) {
        return repository.findAll(specification, pageable);
    }

    public Page<R> getPagging(Pageable pageable, Specification<R> specification, Boolean includeDeleted) {

        Session session = entityManager.unwrap(Session.class);

        if (includeDeleted){
            session.enableFilter("softDeleteFilter");
        }

        return repository.findAll(specification, pageable);
    }
}
