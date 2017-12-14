package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class MealController extends AbstractMealController {

    @Autowired
    public MealController(MealService service) {
        super(service);
    }

    @GetMapping
    public String root(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @PostMapping
    public String postSave(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            create(meal);
        } else {
            update(meal, getId(request));
        }
        return "redirect:/meals";
    }

    @GetMapping("/{id}/delete")
    public String getDelete(@PathVariable int id, HttpServletRequest request) {
        delete(id);
        log.info(request.getContextPath());
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String getCreate(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        return getMeal(model, meal, "create");
    }

    @GetMapping("/{id}/update")
    public String getUpdate(@PathVariable int id, Model model) {
        Meal meal = get(id);
        return getMeal(model, meal, "update");
    }

    private String getMeal(Model model, Meal meal, String action) {
        model.addAttribute("action", action);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/filter")
    public String getFiltered(Model model, HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }
}
