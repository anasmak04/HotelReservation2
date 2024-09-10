package main.java.ui;

import main.java.entities.Reservation;
import main.java.service.ClientService;
import main.java.service.ReservationService;
import main.java.service.RoomService;
import main.java.service.StatisticsService;

import java.util.List;
import java.util.Scanner;

public class ReservationMenu {

    private final ReservationService reservationService;
    private final ClientService clientService;
    private final RoomService roomService;
    private final StatisticsService statisticsService;
    private Scanner scanner;

    public ReservationMenu(ReservationService reservationService, ClientService clientService, RoomService roomService, StatisticsService statisticsService) {
        this.reservationService = reservationService;
        this.clientService = clientService;
        this.roomService = roomService;
        this.statisticsService = statisticsService;
        this.scanner = new Scanner(System.in);
    }

    public void reservationMenu() {
        while (true) {
            System.out.println("\n--- Reservation Menu ---");
            System.out.println("1. Create Reservation");
            System.out.println("2. Show All Reservations");
            System.out.println("3. Show Reservation By Id");
            System.out.println("4. Update Reservation By Id");
            System.out.println("5. Delete Reservation By Id");
            System.out.println("6. Deleted Reservations");
            System.out.println("7. Clients Menu");
            System.out.println("8. Rooms Menu");
            System.out.println("9. Reservation statistics");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        save();
                        break;
                    case 2:
                        findAll();
                        break;
                    case 3:
                        findById();
                        break;
                    case 4:
                        update();
                        break;
                    case 5:
                        delete();
                        break;
                    case 6:
                        findAllDeletedReservations();
                        break;
                    case 7:
                        clientMenu(roomService, clientService, reservationService);
                        break;
                    case 8:
                        roomMenu(roomService, clientService, reservationService);
                        break;
                    case 9:
                        statisticMenu(statisticsService, reservationService, roomService, clientService);
                        break;
                    case 10:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
                scanner.next();
            }
        }
    }

    public void save() {
        reservationService.save();
    }

    public void update() {
        reservationService.update();
    }

    public void delete() {
        reservationService.delete();
    }

    public void findById() {
        Reservation fetchedReservation = reservationService.findById();
        System.out.printf("%-15s %-15s %-15s %-20s %-25s %-10s%n",
                "Reservation ID", "Start Date", "End Date", "Room Name", "Client Name", "Status");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-15s %-15s %-20s %-25s %-10s%n",
                fetchedReservation.getReservationId(),
                fetchedReservation.getStartDate(),
                fetchedReservation.getEndDate(),
                fetchedReservation.getRoom().getRoomName(),
                fetchedReservation.getClient().getFirstName() + " " + fetchedReservation.getClient().getLastName(),
                fetchedReservation.getStatus());

    }

    public void findAll() {
        List<Reservation> reservations = reservationService.findAll();


        System.out.printf("%-15s %-15s %-15s %-20s %-25s %-10s%n",
                "Reservation ID", "Start Date", "End Date", "Room Name", "Client Name", "Status");
        System.out.println("----------------------------------------------------------------------------------------------------------");

        reservations.forEach(reservation ->
                System.out.printf("%-15s %-15s %-15s %-20s %-25s %-10s%n",
                        reservation.getReservationId(),
                        reservation.getStartDate(),
                        reservation.getEndDate(),
                        reservation.getRoom().getRoomName(),
                        reservation.getClient().getFirstName() + " " + reservation.getClient().getLastName(),
                        reservation.getStatus())
        );
    }


    public void findAllDeletedReservations() {
        reservationService.findAllDeletedReservations()
                .forEach(reservation -> System.out.println(
                        "Reservation ID: " + reservation.getReservationId() +
                                ", Start Date: " + reservation.getStartDate() +
                                ", End Date: " + reservation.getEndDate() +
                                ", Room Name: " + (reservation.getRoom().getRoomName()) +
                                ", Client Name: " + (reservation.getClient().getFirstName() + " " + reservation.getClient().getLastName()) +
                                ", Status: " + reservation.getStatus()
                ));
    }

    public void clientMenu(RoomService roomService, ClientService clientService, ReservationService reservationService) {
        ClientMenu clientMenu = new ClientMenu(clientService, reservationService, roomService, statisticsService);
        clientMenu.clientMenu();
    }


    public void roomMenu(RoomService roomService, ClientService clientService, ReservationService reservationService) {
        RoomMenu roomMenu = new RoomMenu(roomService, clientService, reservationService, statisticsService);
        roomMenu.roomMenu();
    }

    public void statisticMenu(StatisticsService statisticsServicen, ReservationService reservationService, RoomService roomService, ClientService clientService) {
        StatisticsMenu statisticsMenu = new StatisticsMenu(statisticsService, reservationService, clientService, roomService);
        statisticsMenu.statisticsMenu();
    }

}