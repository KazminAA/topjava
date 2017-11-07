package ru.javawebinar.topjava.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class GenericIMDaoImpl<T> implements GenericDao<T> {
    private AtomicInteger counter;
    private ConcurrentMap<Integer, T> storage;

    public GenericIMDaoImpl() {
        counter = new AtomicInteger(1);
        storage = new ConcurrentHashMap<>();
    }

    @Override
    public int create(T newInstance) {
        int id = counter.getAndIncrement();
        storage.put(id, newInstance);
        return id;
    }

    @Override
    public T read(int id) {
        return storage.get(id);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    public ConcurrentMap<Integer, T> getStorage() {
        return storage;
    }
}
