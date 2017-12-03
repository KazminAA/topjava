package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;
import static ru.javawebinar.topjava.Profiles.DATAJPA;

@ActiveProfiles(profiles = {DATAJPA, ACTIVE_DB})
public class DataJpaMealServiceTest extends AbstractMealServiceTest{
}
