package main.java.service;

import main.java.entities.Client;
import main.java.entities.Reservation;
import main.java.entities.ReservationStatus;
import main.java.entities.Room;
import main.java.exception.ClientNotFoundException;
import main.java.exception.RoomNotFoundException;
import main.java.repository.ClientRepository;
import main.java.repository.ReservationRepository;
import main.java.repository.RoomRepository;
import main.java.utils.DateFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final ClientRepository clientRepository;
    private final Scanner scanner;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository, ClientRepository clientRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.clientRepository = clientRepository;
        this.scanner = new Scanner(System.in);
    }

    public Reservation save() {
        System.out.println("Enter reservation start date (yyyy/MM/dd):");
        String startDate = scanner.nextLine();
        LocalDate startDateParse = DateFormat.parseDate(startDate);

        System.out.println("Enter reservation end date (yyyy/MM/dd):");
        String endDate = scanner.nextLine();
        LocalDate endDateParse = DateFormat.parseDate(endDate);

        System.out.println("Enter reservation Room Id:");
        Long roomIdToLong = Long.parseLong(scanner.nextLine());

        System.out.println("Enter Client Id:");
        Long clientIdToLong = Long.parseLong(scanner.nextLine());

        System.out.println("Enter reservation Status:");
        ReservationStatus reservationStatus = ReservationStatus.valueOf(scanner.nextLine().toUpperCase()); // Assuming ReservationStatus is an enum

        Room fetchedRoom = null;
        Client fetchedClient = null;

        try {
            fetchedRoom = roomRepository.findById(roomIdToLong);
        } catch (RoomNotFoundException roomNotFoundException) {
            System.out.println(roomNotFoundException.getMessage());
        }

        try {
            fetchedClient = clientRepository.findById(clientIdToLong);
        } catch (ClientNotFoundException clientNotFoundException) {
            System.out.println(clientNotFoundException.getMessage());
        }

        Reservation reservation = new Reservation(0L, startDateParse, endDateParse, fetchedRoom, fetchedClient, reservationStatus);
        return reservationRepository.save(reservation);
    }

    public Reservation update() {
        System.out.println("Enter reservation ID to update:");
        Long reservationId = Long.parseLong(scanner.nextLine());

        Reservation existingReservation = reservationRepository.findById(reservationId);
        if (existingReservation == null) {
            System.out.println("Reservation not found.");
            return null;
        }

        System.out.println("Enter new reservation start date (yyyy/MM/dd) or press Enter to keep current:");
        String startDate = scanner.nextLine();
        if (!startDate.isEmpty()) {
            existingReservation.setStartDate(DateFormat.parseDate(startDate));
        }

        System.out.println("Enter new reservation end date (yyyy/MM/dd) or press Enter to keep current:");
        String endDate = scanner.nextLine();
        if (!endDate.isEmpty()) {
            existingReservation.setEndDate(DateFormat.parseDate(endDate));
        }

        System.out.println("Enter new Room Id or press Enter to keep current:");
        String roomId = scanner.nextLine();
        if (!roomId.isEmpty()) {
            Long roomIdToLong = Long.parseLong(roomId);
            try {
                existingReservation.setRoom(roomRepository.findById(roomIdToLong));
            } catch (RoomNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Enter new Client Id or press Enter to keep current:");
        String clientId = scanner.nextLine();
        if (!clientId.isEmpty()) {
            Long clientIdToLong = Long.parseLong(clientId);
            try {
                existingReservation.setClient(clientRepository.findById(clientIdToLong));
            } catch (ClientNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Enter new reservation status or press Enter to keep current:");
        String status = scanner.nextLine();
        if (!status.isEmpty()) {
            existingReservation.setStatus(ReservationStatus.valueOf(status.toUpperCase()));
        }

        return reservationRepository.update(existingReservation);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getStatus().name().equals("CONFIRMED"))
                .toList();
    }

    public void delete() {
        System.out.println("Enter the reservation ID to delete:");
        Long reservationId = Long.parseLong(scanner.nextLine());

        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        reservationRepository.delete(reservationId);
        System.out.println("Reservation deleted successfully.");
    }

    public Reservation findById() {
        System.out.println("Enter reservation Id");
        String id = scanner.nextLine();
        Long reservationId = Long.parseLong(id);
        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation == null) {
            System.out.println("Reservation not found.");
        }
        return reservation;
    }

    public List<Reservation> findAllDeletedReservations() {
        return findAll().stream()
                .filter(reservation -> reservation.getStatus().name().equals("CANCELLED"))
                .toList();
    }
}
