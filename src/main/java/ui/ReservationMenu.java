package main.java.ui;

import main.java.entities.Reservation;
import main.java.service.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ReservationMenu {

    private final ReservationService reservationService;
    private final ClientService clientService;
    private final RoomService roomService;
    private final StatisticsService statisticsService;
    private final Scanner scanner;
    private final DynamicPricingMenu dynamicPricingMenu;

    public ReservationMenu(ReservationService reservationService, ClientService clientService, RoomService roomService, StatisticsService statisticsService, DynamicPricingMenu dynamicPricingMenu) {
        this.reservationService = reservationService;
        this.clientService = clientService;
        this.roomService = roomService;
        this.statisticsService = statisticsService;
        this.scanner = new Scanner(System.in);
        this.dynamicPricingMenu = dynamicPricingMenu;
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
            System.out.println("10. Dynamic Pricing");
            System.out.println("11. Exit");
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
                        clientMenu();
                        break;
                    case 8:
                        roomMenu();
                        break;
                    case 9:
                        statisticMenu();
                        break;
                    case 10:
                        openDynamicPricingMenu();
                        break;
                    case 11:
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
        Optional<Reservation> fetchedReservation = reservationService.findById();
        if(fetchedReservation.isPresent()) {
            Reservation reservation = fetchedReservation.get();
            System.out.printf("%-15s %-15s %-15s %-20s %-25s %-10s %-15s%n",
                    "Reservation ID", "Start Date", "End Date", "Room Name", "Client Name", "Status", "Total Price");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-15s %-15s %-15s %-20s %-25s %-10s %-15.2f%n",
                    reservation.getReservationId(),
                    reservation.getStartDate(),
                    reservation.getEndDate(),
                    reservation.getRoom().getRoomName(),
                    reservation.getClient().getFirstName() + " " + reservation.getClient().getLastName(),
                    reservation.getStatus(),
                    reservation.getTotalPrice()
            );
        }
    }

    public void findAll() {
        List<Reservation> reservations = reservationService.findAll();

        System.out.printf("%-15s %-15s %-15s %-20s %-25s %-10s %-15s%n",
                "Reservation ID", "Start Date", "End Date", "Room Name", "Client Name", "Status", "Total Price");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");

        reservations.forEach(reservation ->
                System.out.printf("%-15d %-15s %-15s %-20s %-25s %-10s %-15.2f%n",
                        reservation.getReservationId(),
                        reservation.getStartDate(),
                        reservation.getEndDate(),
                        reservation.getRoom().getRoomName(),
                        reservation.getClient().getFirstName() + " " + reservation.getClient().getLastName(),
                        reservation.getStatus(),
                        reservation.getTotalPrice())
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

    public void clientMenu() {
        ClientMenu clientMenu = new ClientMenu(clientService, reservationService, roomService, statisticsService, dynamicPricingMenu);
        clientMenu.clientMenu();
    }

    public void roomMenu() {
        RoomMenu roomMenu = new RoomMenu(roomService, clientService, reservationService, statisticsService, dynamicPricingMenu);
        roomMenu.roomMenu();
    }

    public void statisticMenu() {
        StatisticsMenu statisticsMenu = new StatisticsMenu(statisticsService, reservationService, clientService, roomService,dynamicPricingMenu);
        statisticsMenu.statisticsMenu();
    }

    public void openDynamicPricingMenu() {
        dynamicPricingMenu.PricingMenu();
    }
}
