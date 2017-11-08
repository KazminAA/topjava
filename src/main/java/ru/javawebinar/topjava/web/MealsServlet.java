package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DaoFactory;
import ru.javawebinar.topjava.dao.GenericDao;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExceeded;

public class MealsServlet extends HttpServlet {
    private static final Logger log = getLogger(MealsServlet.class);
    GenericDao<Meal> mealsDao = DaoFactory.getInstance().getMealsDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        log.debug("Save meal on date: " + req.getParameter("date"));
        Meal meal = new Meal(LocalDateTime.parse(req.getParameter("date")),
                req.getParameter("descr"),
                Integer.parseInt(req.getParameter("calories")));
        if (req.getParameter("id") == null) {
            meal.setId(mealsDao.create(meal));
        }
        else {
            meal.setId(Integer.parseInt(req.getParameter("id")));
            mealsDao.update(meal);
        }
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("oper");
        String pathToRedirect = "";
        if ((action == null) || (action.equals(""))) {
            action = "view";
        }
        switch (action) {
            case "view": {
                pathToRedirect = viewMealsList(req);
                break;
            }
            case "add": {
                pathToRedirect = "editmeal.jsp";
                break;
            }
            case "edit": {
                pathToRedirect = editMeal(req);
                break;
            }
            case "delete": {
                pathToRedirect = deleteMeal(req);
                resp.sendRedirect(pathToRedirect);
                return;
            }
        }
        req.getRequestDispatcher(pathToRedirect).forward(req, resp);
    }

    private String deleteMeal(HttpServletRequest req) {
        log.debug("Entering del method with id=" + req.getParameter("id"));
        int id = Integer.parseInt(req.getParameter("id"));
        Meal mealToDel = mealsDao.read(id);
        log.debug("Delete meal for: " + mealToDel.getDate());
        mealsDao.delete(mealToDel);
        return "meals";
    }

    private String editMeal(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("meal", mealsDao.read(id));
        return "editmeal.jsp";
    }

    private String viewMealsList(HttpServletRequest req) {
        List<Meal> meals = mealsDao.getAll();
        req.setAttribute("meals", getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, 2000));
        return "meals.jsp";
    }
}
