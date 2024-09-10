package main.java.validators;

import main.java.entities.Reservation;
import main.java.exception.InvalidInputException;
import main.java.service.RoomService;

public class ReservationValidator {
    private static RoomService roomService;
    public ReservationValidator(RoomService roomService) {
        this.roomService = roomService;
    }

    public static void validatorReservation(Reservation reservation) {
        if (reservation.getStartDate() == null || reservation.getEndDate() == null) {
            throw new InvalidInputException("Reservation start date and end date cannot be null.");
        }
        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            throw new InvalidInputException("Start date cannot be after end date.");
        }
        if (reservation.getEndDate().isBefore(reservation.getStartDate())) {
            throw new InvalidInputException("End date cannot be before start date.");
        }

        if (!roomService.isRoomAvailable(reservation.getRoom().getRoomId(), reservation.getStartDate(), reservation.getEndDate())) {
            System.out.println("Error: The room is already reserved for the given dates. Please choose other dates.");
        }

    }
}
