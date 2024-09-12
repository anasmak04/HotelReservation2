package main.java.service;

import main.java.entities.DynamicPricing;
import main.java.entities.Event;
import main.java.entities.Reservation;
import main.java.entities.Room;
import main.java.enums.Season;

import java.time.DayOfWeek;
import java.time.LocalDate;


public class DynamicPricingService {

    private final DynamicPricing dynamicPricing;

    public DynamicPricingService() {
        this.dynamicPricing = new DynamicPricing();
    }

    public double calculateFinalPrice(Reservation reservation) {
        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();
        Room room = reservation.getRoom();
        double basePrice = room.getPrice();

        long totalNights = startDate.datesUntil(endDate).count();
        double totalPrice = 0.0;

        for (int i = 0; i < totalNights; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            double dailyPrice = basePrice;

            if (isWeekend(currentDate)) {
                dailyPrice *= dynamicPricing.getDayOfWeekRates().getOrDefault("WEEKEND", 0.0);
            } else {
                dailyPrice *= dynamicPricing.getDayOfWeekRates().getOrDefault("WEEKDAY", 0.0);
            }


            dailyPrice *= getSeasonMultiplier(currentDate);
            dailyPrice *= getEventMultiplier(currentDate);

            totalPrice += dailyPrice;
        }

        reservation.setTotalPrice(totalPrice);
        return totalPrice;
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
    }

    private double getSeasonMultiplier(LocalDate date) {
        Season season = getSeasonForDate(date);
        return dynamicPricing.getSeasonRates().getOrDefault(season.name(), 1.0);
    }

    private Season getSeasonForDate(LocalDate date) {
        int month = date.getMonthValue();
        if (month >= 3 && month <= 5) {
            return Season.SPRING;
        } else if (month >= 6 && month <= 8) {
            return Season.SUMMER;
        } else if (month >= 9 && month <= 11) {
            return Season.FALL;
        } else {
            return Season.WINTER;
        }
    }

    private double getEventMultiplier(LocalDate date) {
        double multiplier = 1.0;
        for (Event event : dynamicPricing.getEventRates().values()) {
            if (!date.isBefore(event.getStartDate()) && !date.isAfter(event.getEndDate())) {
                multiplier = Math.max(multiplier, event.getMultiplier());
            }
        }
        return multiplier;
    }

    public void addSpecialEvent(String eventName, LocalDate startDate, LocalDate endDate, double multiplier) {
        dynamicPricing.addEventRate(eventName, startDate, endDate, multiplier);
    }
}
