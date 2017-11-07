package ru.javawebinar.topjava.dao;

import java.util.List;

public interface GenericDao<T>{

    int create(T newInstance);
    T read(int id);
    void update(T trancientInstance);
    void delete(T persistentInstance);

    List<T> getAll();
}
