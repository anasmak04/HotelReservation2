package main.java.service;

import main.java.entities.DynamicPricing;
import main.java.entities.Season;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public class DynamicPricingService {

    private final DynamicPricing dynamicPricing;


    public DynamicPricingService(DynamicPricing dynamicPricing) {
        this.dynamicPricing = dynamicPricing;
    }


    public double calculateBasedOnDay(LocalDate startDate, LocalDate endDate) {
        double total = 0;
        LocalDate currentDate = startDate;
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        String day = dayOfWeek.toString();
        double result = 0;
        while (currentDate.isBefore(endDate)) {
            result = dynamicPricing.getDayOfWeekRates().getOrDefault(day, 0d);
        }
        total = total + result;
        return total;
    }


    public double getSeasonBasedOnMonth(LocalDate startDate, LocalDate endDate) {
        LocalDate currentDate = startDate;
        Month month = currentDate.getMonth();
        String season = "";
        double total = 0d;
        double result;

        switch (month) {
            case MARCH:
            case APRIL:
                season = Season.SPRING.toString();
                break;
            case MAY:
            case JUNE:
            case JULY:
            case AUGUST:
                season = Season.SUMMER.toString();
                break;
            case SEPTEMBER:
            case OCTOBER:
                season = Season.FALL.toString();
                break;
            case NOVEMBER:
            case DECEMBER:
            case JANUARY:
            case FEBRUARY:
                season = Season.WINTER.toString();
                break;
            default:
                season = "";
                break;
        }

        System.out.println(season);
        result = dynamicPricing.getSeasons().getOrDefault(season, 0d);
         total += result;
        return total;


    }


}


