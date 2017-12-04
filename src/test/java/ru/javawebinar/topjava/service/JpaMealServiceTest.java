package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(profiles = {JPA})
public class JpaMealServiceTest extends AbstractMealServiceTest {
}
