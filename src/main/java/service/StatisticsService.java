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
        return allReservations().stream()
//                .filter(reservation -> roomService.isRoomAvailable(reservation.getRoom().getRoomId(),
//                        reservation.getStartDate(), reservation.getEndDate()))
                .mapToDouble(Reservation::getTotalPrice)
                .average()
                .orElse(0.0);
    }
}