package main.java.service;

import main.java.entities.Reservation;
import main.java.repository.ReservationRepository;
import main.java.repository.RoomRepository;

import java.util.List;

public class StatisticsService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public StatisticsService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public List<Reservation> allReservations() {
        return reservationRepository.findAll();
    }

    public long countCancelledReservations() {
        return allReservations().stream()
                .filter(reservation -> reservation.getStatus().name().equals("CANCELLED"))
                .count();
    }


    public double generatedRevenue() {
        return allReservations().stream()
                .mapToDouble(reservation -> reservation.getRoom().getPrice())
                .sum();
    }

    public double occupancyRate() {
        long totalRooms = roomRepository.findAll().size();
        long reservedRooms = allReservations().stream()
                .map(Reservation::getRoom)
                .distinct().count();
        return (double) reservedRooms / totalRooms * 100;
    }

}
