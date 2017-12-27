package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeFormatter implements Formatter<LocalTime> {
    private final DateTimeFormatter formatter;

    public TimeFormatter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public LocalTime parse(String s, Locale locale) throws ParseException {
        return StringUtils.isEmpty(s) ? null : LocalTime.parse(s, formatter);
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        return formatter.format(localTime);
    }
}
