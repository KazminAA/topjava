package ru.javawebinar.topjava.util.formatter;

import org.slf4j.Logger;
import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

public class DateFormatter implements Formatter<LocalDate> {
    private static final Logger log = getLogger(DateFormatter.class);
    private final DateTimeFormatter formatter;

    public DateFormatter(String pattern) {
        log.debug(String.format("pattern for date format: %s", pattern));
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
