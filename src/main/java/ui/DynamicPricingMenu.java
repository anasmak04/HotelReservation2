package main.java.ui;

import main.java.entities.DynamicPricing;
import main.java.service.*;
import java.util.Scanner;

public class DynamicPricingMenu {

    private final DynamicPricingService dynamicPricingService;
    private DynamicPricing dynamicPricing;
    private final Scanner scanner;
    private final ReservationService reservationService;
    private final RoomService roomService;
    private final ClientService clientService;
    private final StatisticsService statisticsService;

    public DynamicPricingMenu(DynamicPricingService dynamicPricingService, ReservationService reservationService, ClientService clientService, RoomService roomService, StatisticsService statisticsService) {
        this.dynamicPricingService = dynamicPricingService;
        this.scanner = new Scanner(System.in);
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.clientService = clientService;
        this.statisticsService = statisticsService;
    }

    public void clientMenu() {

        while (true) {
            System.out.println("1. Add price based on day of the week");
            System.out.println("2. Add price based on season");
            System.out.println("3. Add price based on events");
            System.out.println("4. Main menu");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        reservationMenu(reservationService, clientService, roomService, statisticsService);
                        break;

                    case 7:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice");
                }

            } else {
                System.out.println("Invalid choice");
                scanner.next();
            }
        }

    }


    public void reservationMenu(ReservationService reservationService, ClientService clientService, RoomService roomService, StatisticsService statisticsService) {
        ReservationMenu reservationMenu = new ReservationMenu(reservationService, clientService, roomService, statisticsService);
        reservationMenu.reservationMenu();
    }
}
