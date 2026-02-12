package com.woodstock.app.service.impelment;

import com.woodstock.app.service.interfaces.BaseEntityService;
import com.woodstock.app.utils.exception.CannotFoundTreeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public abstract class BaseServiceImpl<
        T extends JpaRepository<R , UUID>,
        R > implements BaseEntityService<T, R> {

    private final T repository;


    protected BaseServiceImpl(T repository) {
        this.repository = repository;
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

        R r = findbyId(id);
        delete(r);

        return r;
    }

    @Override
    public void delete(R r) {
        repository.delete(r);
    }

    @Override
    public R Update(R r) {
        return repository.save(r);
    }
}
