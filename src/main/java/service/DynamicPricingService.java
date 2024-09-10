package main.java.service;

import main.java.entities.DynamicPricing;
import main.java.entities.Reservation;
import main.java.entities.Room;
import main.java.enums.Season;

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

        while (!currentDate.isAfter(endDate)) {
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            String day = dayOfWeek.toString();
            total += dynamicPricing.getDayOfWeekRates().getOrDefault(day, 0d);
            currentDate = currentDate.plusDays(1);
        }
        return total;
    }

    public double getSeasonBasedOnMonth(LocalDate startDate, LocalDate endDate) {
        double totalSeasonalAdjustment = 0;
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            Month month = currentDate.getMonth();
            String season = getSeasonForMonth(month);
            double seasonRate = dynamicPricing.getSeasons().getOrDefault(season, 0d);
            totalSeasonalAdjustment += seasonRate;
            currentDate = currentDate.plusDays(1);
        }
        return totalSeasonalAdjustment;
    }

    private String getSeasonForMonth(Month month) {
        switch (month) {
            case MARCH:
            case APRIL:
            case MAY:
                return Season.SPRING.toString();
            case JUNE:
            case JULY:
            case AUGUST:
                return Season.SUMMER.toString();
            case SEPTEMBER:
            case OCTOBER:
            case NOVEMBER:
                return Season.FALL.toString();
            case DECEMBER:
            case JANUARY:
            case FEBRUARY:
                return Season.WINTER.toString();
            default:
                return "";
        }
    }


    public double calculateFinalPrice(Reservation reservation) {
        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();
        Room room = reservation.getRoom();
        double basedPricePerNight = room.getPrice();
        long numberOfNights = startDate.datesUntil(endDate).count();
        double totalBasedOnDays = calculateBasedOnDay(startDate, endDate);
        double totalBasedOnSeason = getSeasonBasedOnMonth(startDate, endDate);
        double totalBasePrice = basedPricePerNight * numberOfNights;
        double totalFinalPrice = totalBasePrice + totalBasedOnDays + totalBasedOnSeason;
        reservation.setTotalPrice(totalFinalPrice);
        return totalFinalPrice;
    }


}
