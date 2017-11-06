package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

public class MealsIMDaoImpl extends GenericIMDaoImpl<Meal> {
    @Override
    public void update(Meal trancientInstance) {
        getStorage().put(trancientInstance.getId(), trancientInstance);
    }

    @Override
    public void delete(Meal persistentInstance) {
        getStorage().remove(persistentInstance.getId());
    }
}
