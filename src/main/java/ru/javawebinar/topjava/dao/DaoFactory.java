package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.io.IOException;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

public class DaoFactory {
    private static DaoFactory factory;
    private static final Logger log = getLogger(DaoFactory.class);
    private GenericDao<Meal> mealsDao;

    public GenericDao<Meal> getMealsDao() {
        return mealsDao;
    }

    private DaoFactory() {
        Properties properties = new Properties();
        try {
            properties.load(DaoFactory.class.getClassLoader().getResourceAsStream("application.properties"));
            String daoClassSuffix = properties.getProperty("daoClassSuffix");
            log.debug("try set daoClassSuffix to " + daoClassSuffix);
            mealsDao = (GenericDao<Meal>) Class.forName("ru.javawebinar.topjava.dao.Meals" + daoClassSuffix).newInstance();
            log.debug("mealsDao set to" + mealsDao.getClass().getSimpleName());
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static DaoFactory getInstance() {
        if (factory == null){
            factory = new DaoFactory();
        }
        return factory;
    }
}
