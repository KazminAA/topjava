package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    @Transactional
    Meal save(Meal meal);

    Meal findMealByUserIdAndId(int user_id, int id);

    @Transactional
    int deleteMealByIdAndUserId(int id, int user_id);

    List<Meal> findAllByUserId(int user_id, Sort sort);

    List<Meal> findAllByDateTimeBetweenAndUserId(LocalDateTime startDate, LocalDateTime endDate, int user_id, Sort sort);

    @Query("select m from Meal m left join fetch m.user where m.user.id = :user_id order by m.dateTime desc")
    List<Meal> getAllWithUser(@Param("user_id") int user_id);
}
