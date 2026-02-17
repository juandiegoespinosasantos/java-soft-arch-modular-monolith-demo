package com.jdespinosa.demo.restaurant.commons.services;

import com.jdespinosa.demo.restaurant.commons.exception.NotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Abstract basic service class.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 16, 2026
 * @since 17
 */
@Service
@Transactional(readOnly = true)
public abstract class BasicService<I, E, T, R> implements IBasicService<I, T, R> {

    @Getter
    private final JpaRepository<E, I> repository;

    @Autowired
    protected BasicService(JpaRepository<E, I> repository) {
        this.repository = repository;
    }

    protected abstract String getEntityName();

    protected abstract List<T> transformToTOList(List<E> entities);

    protected abstract T transformToTO(E entity);

    protected abstract E transformToEntity(R requestBody);

    protected abstract void updateEntity(E pivot, R requestBody);

    @Override
    public List<T> findAll() {
        List<E> entities = repository.findAll();

        return transformToTOList(entities);
    }

    @Override
    public Optional<T> find(final I id) {
        Optional<E> opt = repository.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        T dto = transformToTO(opt.get());

        return Optional.of(dto);
    }

    @Override
    @Transactional
    public T create(final R requestBody) {
        E entity = transformToEntity(requestBody);
        entity = repository.save(entity);

        return transformToTO(entity);
    }

    @Override
    @Transactional
    public T update(final I id, final R requestBody) throws NotFoundException {
        Optional<E> pivotOpt = getRepository().findById(id);
        if (pivotOpt.isEmpty()) throw new NotFoundException(getEntityName(), id);

        E pivot = pivotOpt.get();
        updateEntity(pivot, requestBody);

        pivot = getRepository().save(pivot);

        return transformToTO(pivot);
    }

    @Override
    public void delete(I id) throws NotFoundException {
        Optional<E> pivotOpt = repository.findById(id);
        if (pivotOpt.isEmpty()) throw new NotFoundException(getEntityName(), id);

        repository.delete(pivotOpt.get());
    }
}