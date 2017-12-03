package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;
import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(profiles = {JDBC, ACTIVE_DB})
public class JdbcUserServiceTest extends AbstractUserServiceTest {
}
