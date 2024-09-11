package main.java.entities;

import java.util.HashMap;
import java.util.Map;

public class DynamicPricing {


    private final Map<String, Double> dayOfWeekRates = new HashMap<>();
    private final Map<String, Double> seasonRates = new HashMap<>();
    private final Map<String, Double> eventRates = new HashMap<>();

    public DynamicPricing() {
        dayOfWeekRates.put("WEEKDAY", 1.00);
        dayOfWeekRates.put("WEEKEND", 1.50);

        seasonRates.put("SPRING", 1.0);
        seasonRates.put("SUMMER", 1.5);
        seasonRates.put("FALL", 1.0);
        seasonRates.put("WINTER", 0.8);

        eventRates.put("NEW_YEAR", 2.0);
        eventRates.put("CHRISTMAS", 1.8);
    }

    public Map<String, Double> getDayOfWeekRates() {
        return dayOfWeekRates;
    }

    public Map<String, Double> getSeasonRates() {
        return seasonRates;
    }

    public Map<String, Double> getEventRates() {
        return eventRates;
    }

    public void addDayOfWeekRate(String dayType, double multiplier) {
        dayOfWeekRates.put(dayType, multiplier);
    }


    public void addSeasonRate(String season, double multiplier) {
        seasonRates.put(season, multiplier);
    }

    public void addEventRate(String event, double multiplier) {
        eventRates.put(event, multiplier);
    }
}
