package main.java.entities;

import main.java.enums.Events;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DynamicPricing {

    private final Map<String, Double> dayOfWeekRates = new HashMap<>();
    private final Map<String, Double> seasonRates = new HashMap<>();
    private final Map<String, Event> eventRates = new HashMap<>();


    public DynamicPricing() {
        dayOfWeekRates.put("WEEKDAY", 1.00);
        dayOfWeekRates.put("WEEKEND", 1.50);

        seasonRates.put("SPRING", 1.0);
        seasonRates.put("SUMMER", 1.5);
        seasonRates.put("FALL", 1.0);
        seasonRates.put("WINTER", 0.8);

        eventRates.put(Events.HOLIDAY.name(), new Event(LocalDate.of(2024, 12, 24), LocalDate.of(2024, 12, 26), 2.0));
        eventRates.put(Events.NEWYEAR.name(), new Event(LocalDate.of(2024, 12, 31), LocalDate.of(2024, 12, 31), 1.8));
        eventRates.put(Events.CHRISMAS.name(), new Event(LocalDate.of(2024, 12, 25), LocalDate.of(2024, 12, 25), 1.8));
    }

    public Map<String, Double> getDayOfWeekRates() {
        return dayOfWeekRates;
    }

    public Map<String, Double> getSeasonRates() {
        return seasonRates;
    }

    public Map<String, Event> getEventRates() {
        return eventRates;
    }

    public void addDayOfWeekRate(String dayType, double multiplier) {
        dayOfWeekRates.put(dayType, multiplier);
    }

    public void addSeasonRate(String season, double multiplier) {
        seasonRates.put(season, multiplier);
    }

    public void addEventRate(String event, LocalDate startDate, LocalDate endDate, double multiplier) {
        eventRates.put(event, new Event(startDate, endDate, multiplier));
    }
}
