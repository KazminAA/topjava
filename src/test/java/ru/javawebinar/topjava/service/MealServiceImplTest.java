package ru.javawebinar.topjava.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration ({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceImplTest {

    @Autowired
    private MealService mealService;

    @Test
    public void get() throws Exception {
        Meal meal = mealService.get(meal1.getId(), USER_ID);
        assertMatch(meal, meal1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        mealService.get(meal1.getId(), ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        mealService.delete(meal1.getId(), USER_ID);
        mealService.delete(meal2.getId(), USER_ID);
        asserMatch(mealService.getAll(USER_ID), meal6, meal5, meal4, meal3);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        mealService.delete(meal1.getId(), ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> meals = mealService.getBetweenDateTimes(
                LocalDateTime.of(2015, Month.MAY, 31, 0, 0),
                LocalDateTime.of(2015, Month.MAY, 31, 23, 59),
                USER_ID);
        asserMatch(meals, meal6, meal5, meal4);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = mealService.getAll(USER_ID);
        assertMatch(meals, allMeals);
    }

    @Test
    public void getAllNotFound() throws Exception {
        List<Meal> meals = mealService.getAll(ADMIN_ID);
        Assertions.assertThat(meals.isEmpty());
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(meal1);
        updated.setCalories(3000);
        updated.setDescription("Сверхколорийный обед");
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(meal1.getId(), USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateTryNotAllowed() throws Exception {
        mealService.update(meal1, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.MAY, 29, 13, 0), "Обед", 500);
        List<Meal> mealList = new ArrayList<>(allMeals);
        mealList.add(newMeal);
        mealService.create(newMeal, USER_ID);
        assertMatch(mealService.getAll(USER_ID), mealList);
    }

    @Test(expected = DuplicateKeyException.class)
    public void createWithExistingDate() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед2", 550);
        mealService.create(newMeal, USER_ID);
    }
}