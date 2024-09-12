package main.java.entities;

import java.time.LocalDate;

public class Event {
    private LocalDate startDate;
    private LocalDate endDate;
    private double multiplier;

    public Event(LocalDate startDate, LocalDate endDate, double multiplier) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.multiplier = multiplier;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}
