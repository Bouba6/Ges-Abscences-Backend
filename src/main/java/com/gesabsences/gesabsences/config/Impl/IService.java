package com.gesabsences.gesabsences.config.Impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.gesabsences.gesabsences.config.Service;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor

public abstract class IService<T, R extends MongoRepository<T, String>> implements Service<T> {

    protected final R repository;

    @Override
    public T create(T object) {
        return repository.save(object);
    }

    @Override
    public boolean delete(String id) {
        var x = repository.existsById(id);
        if (x) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public T findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Page findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

}
