package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;
import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(profiles = {JPA, ACTIVE_DB})
public class JpaUserServiceTest extends AbstractUserServiceTest {
}
