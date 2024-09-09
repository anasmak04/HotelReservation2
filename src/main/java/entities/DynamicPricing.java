package main.java.entities;


import java.util.LinkedHashMap;
import java.util.Map;

public class DynamicPricing {

    private final Map<String, Double> dayOfWeekRates;

    public DynamicPricing() {
        this.dayOfWeekRates = new LinkedHashMap<>();
    }
}
