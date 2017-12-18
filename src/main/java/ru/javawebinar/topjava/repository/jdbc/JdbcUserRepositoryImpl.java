package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        saveUserRoles(user);
        return user;
    }

    private void saveUserRoles(User user) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
        List<Role> roleList = new ArrayList<>(user.getRoles());
        jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, user.getId());
                preparedStatement.setString(2, roleList.get(i).toString());
            }

            @Override
            public int getBatchSize() {
                return roleList.size();
            }
        });
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users AS u LEFT JOIN user_roles AS ur ON ur.user_id=u.id WHERE u.id=?", new UserRowMepper(), id);
        return DataAccessUtils.singleResult(mergeSameRows(users).values());
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users AS u LEFT JOIN user_roles AS ur ON ur.user_id=u.id WHERE u.email=?", new UserRowMepper(), email);
        return DataAccessUtils.singleResult(mergeSameRows(users).values());
    }

    private Map<Integer, User> mergeSameRows(List<User> users) {
        return users.stream().collect(Collectors.toMap(User::getId, user -> user, (user1, user2) -> {
            user1.getRoles().addAll(user2.getRoles());
            return user1;
        }));
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        Map<Integer, Set<Role>> roleMap = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM user_roles", (RowMapper<Map<Integer, Set<Role>>>) (resultSet, i) -> {
            roleMap.merge(resultSet.getInt("user_id"), getRole(resultSet), (set1, set2) -> {
                set1.addAll(set2);
                return set1;
            });
            return null;
        });
        users.forEach(user -> user.setRoles(roleMap.get(user.getId())));
        return users;
    }

    private static class UserRowMepper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getInt("calories_per_day"),
                    resultSet.getBoolean("enabled"),
                    resultSet.getDate("registered"),
                    getRole(resultSet)
            );
        }
    }

    private static Set<Role> getRole(ResultSet resultSet) throws SQLException {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.valueOf(resultSet.getString("role")));
        return roles;
    }
}
