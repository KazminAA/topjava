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
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExceeded;

public class MealsServlet extends HttpServlet {
    private static final Logger log = getLogger(MealsServlet.class);
    GenericDao<Meal> mealsDao = DaoFactory.getInstance().getMealsDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("oper");
        if ((action == null) || (action.equals(""))) {
            log.debug("getting meals from repository");
            List<Meal> meals = mealsDao.getAll();
            req.setAttribute("meals", getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, 2000));
            log.debug("adding mealswithexceed to request attribute");
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
        }
        else {
            switch (action) {
                case "add": {
                    resp.sendRedirect("editmeal.jsp");
                    break;
                }
                case "edit": {
                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("meal", mealsDao.read(id));
                    req.getRequestDispatcher("editmeal.jsp").forward(req, resp);
                    break;
                }
            }
        }
    }
}
