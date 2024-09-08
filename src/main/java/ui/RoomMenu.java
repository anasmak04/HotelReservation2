package main.java.ui;

import main.java.entities.Room;
import main.java.service.RoomService;

import java.util.Scanner;

public class RoomMenu {

    private final RoomService roomService;
    private Scanner scanner;

    public RoomMenu(RoomService roomService) {
        this.roomService = roomService;
        this.scanner = new Scanner(System.in);
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

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid choice");
                scanner.nextLine();
                continue;
            }

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
                    System.exit(0);
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }


    public void save() {
        roomService.save();
        System.out.println("Room created successfully.");
    }


    public void findAll() {
        roomService.findAll()
                .forEach(room -> System.out.println(
                        "Room ID: " + room.getRoomId() +
                                ", Room Name: " + room.getRoomName() +
                                ", Room Type: " + (room.getRoomType().name()) +
                                ", Price: " + room.getPrice()
                ));
    }


    public void findById() {
        Room room = roomService.findById();
        System.out.println("Room : " + room.getRoomId() + room.getRoomName() + room.getRoomType().name() + room.getPrice());
    }

    public void update() {
        roomService.update();
    }


    public void delete() {
        roomService.delete();
        System.out.println("Room deleted successfully.");
    }

}
