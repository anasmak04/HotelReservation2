package main.java.entities;


import java.util.LinkedHashMap;
import java.util.Map;

public class DynamicPricing {

    private final Map<String, Double> dayOfWeekRates = new LinkedHashMap<>();
    private final Map<String, Double> seasons = new LinkedHashMap<>();

    public DynamicPricing() {
        dayOfWeekRates.put("MONDAY", 5.00);
        dayOfWeekRates.put("TUESDAY", 5.00);
        dayOfWeekRates.put("WEDNESDAY", 5.00);
        dayOfWeekRates.put("THURSDAY", 5.00);
        dayOfWeekRates.put("FRIDAY", 5.00);
        dayOfWeekRates.put("SATURDAY", 10.00);
        dayOfWeekRates.put("SUNDAY", 10.00);

        seasons.put("SPRING", 10.00);
        seasons.put("SUMMER", 10.00);
        seasons.put("FALL", 10.00);
        seasons.put("WINTER", 20.00);
    }


    public Map<String, Double> getDayOfWeekRates() {
        return dayOfWeekRates;
    }

    public Map<String, Double> getSeasons() {
        return seasons;
    }


}
