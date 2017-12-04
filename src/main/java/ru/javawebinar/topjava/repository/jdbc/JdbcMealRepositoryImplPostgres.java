package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.Profiles.POSTGRES_DB;

@Profile(POSTGRES_DB)
@Repository
public class JdbcMealRepositoryImplPostgres extends AbstractJdbcMealRepositoryImpl implements MealRepository {

    @Autowired
    public JdbcMealRepositoryImplPostgres(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public <T> T getTimestamp(LocalDateTime dateTime) {
        return (T) dateTime;
    }
}
