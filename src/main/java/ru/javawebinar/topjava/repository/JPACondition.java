package ru.javawebinar.topjava.repository;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import ru.javawebinar.topjava.Profiles;

public class JPACondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return !"jdbc".equals(Profiles.REPOSITORY_IMPLEMENTATION);
    }
}
