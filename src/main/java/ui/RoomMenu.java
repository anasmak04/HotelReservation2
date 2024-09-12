package main.java.ui;

import main.java.entities.Room;
import main.java.service.ClientService;
import main.java.service.ReservationService;
import main.java.service.RoomService;
import main.java.service.StatisticsService;

import java.util.List;
import java.util.Scanner;

public class RoomMenu {

    private final RoomService roomService;
    private final ClientService clientService;
    private final ReservationService reservationService;
    private final StatisticsService statisticsService;
    private Scanner scanner;
    private DynamicPricingMenu dynamicPricingMenu;
    public RoomMenu(RoomService roomService, ClientService clientService, ReservationService reservationService, StatisticsService statisticsService, DynamicPricingMenu dynamicPricingMenu) {
        this.roomService = roomService;
        this.clientService = clientService;
        this.reservationService = reservationService;
        this.statisticsService = statisticsService;
        this.scanner = new Scanner(System.in);
        this.dynamicPricingMenu = dynamicPricingMenu;
    }


    public void roomMenu() {
        while (true) {
            System.out.println("1. Create Room");
            System.out.println("2. Show Room");
            System.out.println("3. Show Room By Id");
            System.out.println("4. Update Room By Id");
            System.out.println("5. Delete Room By Id");
            System.out.println("6. Principal Menu");
            System.out.println("7. Exit");
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
                    reservationMenu(reservationService, roomService, clientService, statisticsService,dynamicPricingMenu);
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            }else{
                System.out.println("Please Enter a valid choice");
                scanner.next();

            }
        }
    }


    public void save() {
        roomService.save();
    }


    public void findAll() {
        List<Room> rooms = roomService.findAll();

        System.out.printf("%-10s %-20s %-15s %-10s%n",
                "Room ID", "Room Name", "Room Type", "Price");
        System.out.println("-------------------------------------------------------------");

        rooms.forEach(room ->
                System.out.printf("%-10s %-20s %-15s %-10.2f%n",
                        room.getRoomId(),
                        room.getRoomName(),
                        room.getRoomType().name(),
                        room.getPrice())
        );
    }


    public void findById() {
        Room room = roomService.findById();
        System.out.printf("%-10s %-20s %-15s %-10s%n",
                "Room ID", "Room Name", "Room Type", "Price");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-15s %-10.2f%n",
                room.getRoomId(),
                room.getRoomName(),
                room.getRoomType().name(),
                room.getPrice());
    }

    public void update() {
        roomService.update();
    }


    public void delete() {
        roomService.delete();
        System.out.println("Room deleted successfully.");
    }

    public void reservationMenu(ReservationService reservationService, RoomService roomService, ClientService clientService, StatisticsService statisticsService, DynamicPricingMenu dynamicPricingMenu) {
        ReservationMenu reservationMenu = new ReservationMenu(reservationService, clientService, roomService, statisticsService, dynamicPricingMenu);
        reservationMenu.reservationMenu();
    }

}
