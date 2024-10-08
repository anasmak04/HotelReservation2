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
    private final DynamicPricingMenu dynamicPricingMenu;

    public StatisticsMenu(StatisticsService statisticsService, ReservationService reservationService, ClientService clientService, RoomService roomService, DynamicPricingMenu dynamicPricingMenu) {
        this.statisticsService = statisticsService;
        this.reservationService = reservationService;
        this.clientService = clientService;
        this.roomService = roomService;
        this.dynamicPricingMenu = dynamicPricingMenu;
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
                    countCancelledReservations();
                    break;
                case 2:
                    generatedRevenue();
                    break;
                case 3:
                    occupancyRate();
                    break;
                case 4:
                    reservationMenu(reservationService, roomService, clientService, statisticsService,dynamicPricingMenu);
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

    private void countCancelledReservations() {
        long count = statisticsService.countCancelledReservations();
        System.out.println(count + " reservations have been cancelled.");
    }

    private void generatedRevenue() {
        double generatedRevenue = statisticsService.generatedRevenue();
        System.out.println(generatedRevenue + " revenues have been generated.");
    }

    private void occupancyRate() {
         double rates = statisticsService.occupancyRate();
        System.out.println(String.format("%.2f", rates)  + " occupancy rates have been occupied. %");
    }

    public void reservationMenu(ReservationService reservationService, RoomService roomService, ClientService clientService, StatisticsService statisticsService, DynamicPricingMenu dynamicPricingMenu) {
        ReservationMenu reservationMenu = new ReservationMenu(reservationService, clientService, roomService, statisticsService,dynamicPricingMenu);
        reservationMenu.reservationMenu();
    }
}
