package main.java.service;

import main.java.entities.*;
import main.java.enums.ReservationStatus;
import main.java.exception.ClientNotFoundException;
import main.java.exception.ReservationNotFoundException;
import main.java.exception.RoomNotFoundException;
import main.java.repository.ClientRepository;
import main.java.repository.ReservationRepository;
import main.java.repository.RoomRepository;
import main.java.utils.DateFormat;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    public void save() {

        System.out.print("Enter reservation start date (yyyy/MM/dd) : ");
        String startDate = scanner.nextLine();
        LocalDate startDateParse = DateFormat.parseDate(startDate);

        System.out.print("Enter reservation end date (yyyy/MM/dd) : ");
        String endDate = scanner.nextLine();
        LocalDate endDateParse = DateFormat.parseDate(endDate);

        System.out.print("Enter reservation Room Id : ");
        Long roomIdToLong = Long.parseLong(scanner.nextLine());

        System.out.print("Enter Client Id : ");
        Long clientIdToLong = Long.parseLong(scanner.nextLine());

        System.out.print("Enter reservation Status : ");
        ReservationStatus reservationStatus = ReservationStatus.valueOf(scanner.nextLine().toUpperCase());

        Optional<Room> fetchedRoom = null;
        Optional<Client> fetchedClient = null;

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

        Reservation reservation = new Reservation(0L, startDateParse, endDateParse, fetchedRoom.get(), fetchedClient.get(), reservationStatus);

        reservationRepository.save(reservation);
    }

    public void update() {
        System.out.print("Enter reservation Id to update : ");
        String reservationId = scanner.nextLine();
        Long reservationIdToLong = Long.parseLong(reservationId);
        Optional<Reservation> fetchedReservation = reservationRepository.findById(Long.parseLong(reservationId));
        if (fetchedReservation.isPresent()) {
            throw new ReservationNotFoundException("Reservation Not Found ! ");
        }

        System.out.print("Enter reservation start date (yyyy/MM/dd) : ");
        String startDate = scanner.nextLine();
        LocalDate startDateParse = DateFormat.parseDate(startDate);
        if (startDate.isEmpty() || startDateParse.isBefore(LocalDate.now())) {
            System.out.println("Enter a valid start date (yyyy/MM/dd):");
        }

        System.out.print("Enter reservation end date (yyyy/MM/dd) : ");
        String endDate = scanner.nextLine();
        LocalDate endDateParse = DateFormat.parseDate(endDate);
        if (endDate.isEmpty() || endDateParse.isBefore(LocalDate.now())) {
            System.out.println("Enter a valid end date (yyyy/MM/dd):");
        }

        System.out.print("Enter reservation Room Id : ");
        Long roomIdToLong = Long.parseLong(scanner.nextLine());
        Optional<Room> fetchedRoom = null;
        try {
            fetchedRoom = roomRepository.findById(roomIdToLong);
        } catch (RoomNotFoundException roomNotFoundException) {
            System.out.println(roomNotFoundException.getMessage());
        }

        System.out.print("Enter reservation client Id : ");
        Long clientIdToLong = Long.parseLong(scanner.nextLine());
        Optional<Client> fetchedClient = null;
        try {
            fetchedClient = clientRepository.findById(clientIdToLong);
        } catch (ClientNotFoundException clientNotFoundException) {
            System.out.println(clientNotFoundException.getMessage());
        }

        System.out.print("Enter reservation Status : ");
        String status = scanner.nextLine().toUpperCase();
        ReservationStatus reservationStatus = ReservationStatus.valueOf(status);

        Reservation reservation = new Reservation(reservationIdToLong, startDateParse, endDateParse, fetchedRoom.get(), fetchedClient.get(), reservationStatus);

        reservationRepository.update(reservation);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getStatus().name().equals("CONFIRMED"))
                .toList();
    }

    public void delete() {
        System.out.print("Enter the reservation ID to delete : ");
        Long reservationId = Long.parseLong(scanner.nextLine());

        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            System.out.println("Reservation not found.");
            return;
        }

        reservationRepository.delete(reservationId);
        System.out.println("Reservation deleted successfully.");
    }

    public Optional<Reservation> findById() {
        System.out.print("Enter reservation ID: ");
        String id = scanner.nextLine();
        Long reservationId = Long.parseLong(id);
        return reservationRepository.findById(reservationId);
    }

    public List<Reservation> findAllDeletedReservations() {
        return findAll().stream()
                .filter(reservation -> reservation.getStatus().name().equals("CANCELLED"))
                .toList();
    }
}