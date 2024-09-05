package main.java.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormat {

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public LocalDate parseDate(String date) {
        return LocalDate.parse(date, dateFormat);
    }
}
