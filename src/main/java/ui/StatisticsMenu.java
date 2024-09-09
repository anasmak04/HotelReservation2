package main.java.ui;

import main.java.service.ClientService;
import main.java.service.ReservationService;
import main.java.service.RoomService;
import main.java.service.StatisticsService;

import java.util.Scanner;

public class StatisticsMenu {
    private final StatisticsService statisticsService;
    private final ReservationService reservationService;
    private final ClientService clientService;
    private final RoomService roomService;
    private final Scanner scanner;

    public StatisticsMenu(StatisticsService statisticsService, ReservationService reservationService, ClientService clientService, RoomService roomService) {
        this.statisticsService = statisticsService;
        this.reservationService = reservationService;
        this.clientService = clientService;
        this.roomService = roomService;
        this.scanner = new Scanner(System.in);
    }

    public void statisticsMenu() {
        while (true) {
            System.out.println("Statistics Menu:");
            System.out.println("1. Count Cancelled Reservations");
            System.out.println("2. Generate Revenue");
            System.out.println("3. Occupancy Rate");
            System.out.println("4. Principal Menu");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Cancelled Reservations: " + countCancelledReservations());
                    break;
                case 2:
                    System.out.println("Generated Revenue: $" + generatedRevenue());
                    break;
                case 3:
                    System.out.println("Occupancy Rate: " + occupancyRate() + "%");
                    break;
                case 4:
                    reservationMenu(reservationService, roomService, clientService, statisticsService);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private long countCancelledReservations() {
        return statisticsService.countCancelledReservations();
    }

    private double generatedRevenue() {
        return statisticsService.generatedRevenue();
    }

    private double occupancyRate() {
        return statisticsService.occupancyRate();
    }

    public void reservationMenu(ReservationService reservationService, RoomService roomService, ClientService clientService, StatisticsService statisticsService) {
        ReservationMenu reservationMenu = new ReservationMenu(reservationService, clientService, roomService, statisticsService);
        reservationMenu.reservationMenu();
    }
}
