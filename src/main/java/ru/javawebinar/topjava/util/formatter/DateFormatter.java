package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDate> {
    private final DateTimeFormatter formatter;

    public DateFormatter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        return StringUtils.isEmpty(s) ? null : LocalDate.parse(s, formatter);
    }

    @Override
    public String print(LocalDate date, Locale locale) {
        return formatter.format(date);
    }
}
