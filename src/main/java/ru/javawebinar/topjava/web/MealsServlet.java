package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExceeded;

public class MealsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2017, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2017, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2017, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2017, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2017, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2017, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        req.setAttribute("meals", getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, 2000));
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
