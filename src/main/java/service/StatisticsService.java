package main.java.service;

import main.java.entities.Reservation;
import main.java.repository.ReservationRepository;
import main.java.repository.RoomRepository;
import java.util.List;


public class StatisticsService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final RoomService roomService;

    public StatisticsService(ReservationRepository reservationRepository, RoomRepository roomRepository, RoomService roomService) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.roomService = roomService;
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
                .filter(reservation -> reservation.getStatus().name().equals("CONFIRMED"))
                .mapToDouble(Reservation::getTotalPrice)
                .sum();
    }


    public double occupancyRate() {
        long totalRooms = roomRepository.findAll().size();
        long reservedRooms = allReservations().stream()
                .filter(reservation -> roomService.isRoomAvailable(reservation.getRoom().getRoomId(),
                        reservation.getStartDate(), reservation.getEndDate()))
                .map(Reservation::getRoom)
                .distinct().count();
        return (double) reservedRooms / totalRooms * 100;
    }


//    public Client getMostUserReserved(){
//        List<Reservation> reservations = allReservations();
//        return reservations.stream()
//                .map(Reservation::getClient)
//                .max(Comparator.comparingInt(client -> client.getReservations().size()))
//                .orElse(null);
//    }


}