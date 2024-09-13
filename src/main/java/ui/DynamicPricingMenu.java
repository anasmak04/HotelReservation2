package main.java.ui;

import main.java.service.*;
import main.java.utils.DateFormat;

import java.time.LocalDate;
import java.util.Scanner;

public class DynamicPricingMenu {

    private final DynamicPricingService dynamicPricingService;
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

    public void PricingMenu() {
        while (true) {
            System.out.println("1. Add Special Event : ");
            System.out.println("2. Main menu");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addSpecialEvent();
                        break;
                    case 2:
                        reservationMenu();
                        break;
                    case 3:
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

    public void addSpecialEvent() {
        System.out.print("Enter Event Name : ");
        String eventName = scanner.nextLine();
        System.out.print("Enter Start Date of Event : ");
        String startDate = scanner.nextLine();
        LocalDate startDateParse = DateFormat.parseDate(startDate);
        System.out.print("Enter End Date of Event : ");
        String endDate = scanner.nextLine();
        LocalDate eventDateParse = DateFormat.parseDate(endDate);
        System.out.print("Enter Multiplier of event : ");
        double multiplier = scanner.nextDouble();
        dynamicPricingService.addSpecialEvent(eventName, startDateParse, eventDateParse, multiplier);
    }

    public void reservationMenu() {
        ReservationMenu reservationMenu = new ReservationMenu(reservationService, clientService, roomService, statisticsService, this);
        reservationMenu.reservationMenu();
    }

}
