package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dayCalories = new HashMap<>();
        return mealList.stream()
                .peek(o -> {
                    if (dayCalories.containsKey(TimeUtil.toLocalDate(o.getDateTime()))) {
                        dayCalories.merge(TimeUtil.toLocalDate(o.getDateTime()), o.getCalories(),
                                (v1, v2) -> v1 + v2);
                    } else dayCalories.put(TimeUtil.toLocalDate(o.getDateTime()), o.getCalories());
                }).sorted((o1, o2) -> 0)
                .filter(o -> TimeUtil.isBetween(TimeUtil.toLocalTime(o.getDateTime()),
                        startTime, endTime))
                .map(o -> new UserMealWithExceed(o.getDateTime(), o.getDescription(),
                        o.getCalories(), dayCalories.get(TimeUtil.toLocalDate(o.getDateTime())) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
