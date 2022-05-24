package com.example.application.test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.example.application.data.entity.SamplePerson;
import com.example.application.data.service.SamplePersonRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

public class InMemorySamplePersonRepository implements SamplePersonRepository {

    private final Map<UUID, SamplePerson> store = new LinkedHashMap<>();

    @Override
    public List<SamplePerson> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public List<SamplePerson> findAll(Sort sort) {
        // TODO
        return List.copyOf(store.values());
    }

    @Override
    public Page<SamplePerson> findAll(Pageable pageable) {
        List<SamplePerson> list = store.values().stream()
                .skip(pageable.getOffset()).limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, store.size());
    }

    @Override
    public List<SamplePerson> findAllById(Iterable<UUID> uuids) {
        return StreamSupport.stream(uuids.spliterator(), false).map(store::get)
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return store.size();
    }

    @Override
    public void deleteById(UUID uuid) {
        store.remove(uuid);
    }

    @Override
    public void delete(SamplePerson entity) {
        store.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {
        for (UUID id : uuids) {
            store.remove(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends SamplePerson> entities) {
        for (SamplePerson e : entities) {
            store.remove(e.getId());
        }
    }

    @Override
    public void deleteAll() {
        store.clear();
    }

    @Override
    public <S extends SamplePerson> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends SamplePerson> List<S> saveAll(Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::save).collect(Collectors.toList());
    }

    @Override
    public Optional<SamplePerson> findById(UUID uuid) {
        return Optional.ofNullable(store.get(uuid));
    }

    @Override
    public boolean existsById(UUID uuid) {
        return store.containsKey(uuid);
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends SamplePerson> S saveAndFlush(S entity) {
        return save(entity);
    }

    @Override
    public <S extends SamplePerson> List<S> saveAllAndFlush(
            Iterable<S> entities) {
        return saveAll(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<SamplePerson> entities) {
        deleteAll(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<UUID> uuids) {
        deleteAllById(uuids);
    }

    @Override
    public void deleteAllInBatch() {
        deleteAll();
    }

    @Override
    public SamplePerson getOne(UUID uuid) {
        return store.get(uuid);
    }

    @Override
    public SamplePerson getById(UUID uuid) {
        return store.get(uuid);
    }

    @Override
    public SamplePerson getReferenceById(UUID uuid) {
        return store.get(uuid);
    }

    @Override
    public <S extends SamplePerson> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends SamplePerson> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends SamplePerson> List<S> findAll(Example<S> example,
            Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends SamplePerson> Page<S> findAll(Example<S> example,
            Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends SamplePerson> long count(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends SamplePerson> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends SamplePerson, R> R findBy(Example<S> example,
            Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException();
    }
}
