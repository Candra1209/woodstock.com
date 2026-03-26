package com.woodstock.app.service.impelment;

import com.woodstock.app.service.interfaces.BaseEntityService;
import com.woodstock.app.utils.exception.tree_type.TreeTypeNotFoundException;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class BaseServiceImpl<
        T extends JpaRepository<R , UUID> & JpaSpecificationExecutor<R>,
        R > implements BaseEntityService<T, R> {

    protected final T repository;
    private final EntityManager entityManager;


    protected BaseServiceImpl(T repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public R save(R r) {
        log.info("save new record to database");
        return repository.save(r);
    }

    @Override
    public List<R> saveAll(List<R> r) {
        return repository.saveAll(r);
    }

    @Override
    public R findbyId(UUID id) {

        log.info("try find record by id from database");
        return repository.findById(id)
                .orElseThrow(
                        () -> new TreeTypeNotFoundException("there no record with required id : " + id)
                );
    }

    @Override
    public List<R> findAll() {
        return repository.findAll();
    }

    @Override
    public R delete(UUID id) {
        log.info("try find data from by his id" );
        R r = findbyId(id);
        repository.delete(r);

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

    public R getReferenceById(UUID id) {
        return repository.getReferenceById(id);
    }

    public boolean existById(UUID id){
        return repository.existsById(id);
    }
}
