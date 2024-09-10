package main.java.entities;


import java.util.LinkedHashMap;
import java.util.Map;

public class DynamicPricing {

    private final Map<String, Double> dayOfWeekRates = new LinkedHashMap<>();
    private final Map<String, Double> seasons = new LinkedHashMap<>();

    public DynamicPricing() {
        dayOfWeekRates.put("MONDAY", 1000.00);
        dayOfWeekRates.put("TUESDAY", 1000.00);
        dayOfWeekRates.put("WEDNESDAY", 1000.00);
        dayOfWeekRates.put("THURSDAY", 1000.00);
        dayOfWeekRates.put("FRIDAY", 1000.00);
        dayOfWeekRates.put("SATURDAY", 1500.00);
        dayOfWeekRates.put("SUNDAY", 1500.00);

        seasons.put("SPRING", 1500.00);
        seasons.put("SUMMER", 1000.00);
        seasons.put("FALL", 1000.00);
        seasons.put("WINTER", 1000.00);
    }


    public Map<String, Double> getDayOfWeekRates() {
        return dayOfWeekRates;
    }

    public Map<String, Double> getSeasons() {
        return seasons;
    }


}
