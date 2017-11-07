package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DaoFactory;
import ru.javawebinar.topjava.dao.GenericDao;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class ContextListener implements ServletContextListener {
    private static final Logger log = getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2017, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2017, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2017, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2017, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2017, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2017, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        GenericDao<Meal> reposit = DaoFactory.getInstance().getMealsDao();
        log.debug("add meals to Repository");
        for (Meal meal : meals) {
            meal.setId(reposit.create(meal));
            log.debug("id of record was assigned to: " + meal.getId());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.debug("destroy servlet context");
    }
}
