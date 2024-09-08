package main.java.entities;


import java.time.LocalDate;
import java.util.Date;


public class Reservation {
    private Long reservationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Room room;
    private Client client;
    private ReservationStatus status;


    public Reservation(){}

    public Reservation(Long reservationId, LocalDate startDate, LocalDate endDate, Room room, Client client, ReservationStatus status) {
        this.reservationId = reservationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
        this.client = client;
        this.status = status;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
