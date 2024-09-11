package main.java.service;

import main.java.entities.DynamicPricing;
import main.java.entities.Reservation;
import main.java.entities.Room;
import main.java.enums.Season;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class DynamicPricingService {

    private final DynamicPricing dynamicPricing;

    public DynamicPricingService() {
        this.dynamicPricing = new DynamicPricing();
    }

    public double calculateFinalPrice(Reservation reservation) {
        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();
        Room room = reservation.getRoom();
        double basePricePerNight = room.getPrice();
        long totalNights = startDate.datesUntil(endDate).count();

        long weekendCount = countWeekends(startDate, endDate);
        long weekdayCount = totalNights - weekendCount;
        long eventCount = countEventDays(startDate, endDate);



        double seasonMultiplier = getSeasonMultiplierForPeriod(startDate, endDate);
        double weekdayMultiplier = dynamicPricing.getDayOfWeekRates().getOrDefault("WEEKDAY", 1.0);
        double weekendMultiplier = dynamicPricing.getDayOfWeekRates().getOrDefault("WEEKEND", 1.0);
        double eventMultiplier = 2.0;

        double weekdayPrice = weekdayCount * basePricePerNight * seasonMultiplier * weekdayMultiplier;
        double weekendPrice = weekendCount * basePricePerNight * seasonMultiplier * weekendMultiplier;
        double eventPrice = eventCount * basePricePerNight * seasonMultiplier * eventMultiplier;

        double totalFinalPrice = weekdayPrice + weekendPrice + eventPrice;

        reservation.setTotalPrice(totalFinalPrice);
        return totalFinalPrice;
    }

    private double getSeasonMultiplierForPeriod(LocalDate startDate, LocalDate endDate) {
        String season = getSeasonForDate(startDate);
        return dynamicPricing.getSeasonRates().getOrDefault(season, 1.0);
    }

    private String getSeasonForDate(LocalDate date) {
        int month = date.getMonthValue();
        if (month >= 3 && month <= 5) {
            return Season.SPRING.name();
        } else if (month >= 6 && month <= 8) {
            return Season.SUMMER.name();
        } else if (month >= 9 && month <= 11) {
            return Season.FALL.name();
        } else {
            return Season.WINTER.name();
        }
    }

    private long countWeekends(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate)
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
                .count();
    }

    private long countEventDays(LocalDate startDate, LocalDate endDate) {
        Map<String, Double> events = dynamicPricing.getEventRates();
        return startDate.datesUntil(endDate)
                .filter(date -> events.containsKey(date.toString()))
                .count();
    }

    public void addSpecialEvent(String date, double multiplier) {
        dynamicPricing.getEventRates().put(date, multiplier);
    }


}
