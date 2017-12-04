package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.DATAJPA;

@ActiveProfiles(profiles = {DATAJPA})
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
}
