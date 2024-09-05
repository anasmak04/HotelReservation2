package main.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reservation {
    private Long reservationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Room room;
    private Client client;
    private ReservationStatus status;
}
