package main.java.ui;

import main.java.entities.Client;
import main.java.service.ClientService;
import main.java.service.ReservationService;
import main.java.service.RoomService;
import main.java.service.StatisticsService;

import java.util.List;
import java.util.Scanner;

public class ClientMenu {

    private  ClientService clientService;
    private  ReservationService reservationService;
    private  RoomService roomService;
    private  StatisticsService statisticsService;
    private Scanner scanner;
    public ClientMenu() {}
    public ClientMenu(ClientService clientService, ReservationService reservationService, RoomService roomService, StatisticsService statisticsService) {
        this.clientService = clientService;
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.statisticsService = statisticsService;
        this.scanner = new Scanner(System.in);
    }


    public void clientMenu() {

        while (true) {
            System.out.println("1. Create Client");
            System.out.println("2. Show Client");
            System.out.println("3. Show Client By Id");
            System.out.println("4. Update Client By Id");
            System.out.println("5. Delete Client By Id");
            System.out.println("6. Principal menu");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
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
                    reservationMenu(reservationService, roomService, clientService, statisticsService);
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
        clientService.save();
    }

    public void findAll() {
        List<Client> clients = clientService.findAll();

        System.out.printf("%-10s %-15s %-15s %-15s%n",
                "Client ID", "First Name", "Last Name", "Phone");
        System.out.println("------------------------------------------------------");

        clients.forEach(client ->
                System.out.printf("%-10s %-15s %-15s %-15s%n",
                        client.getClientId(),
                        client.getFirstName(),
                        client.getLastName(),
                        client.getPhone())
        );
    }


    public void findById() {
        Client fetchedClient = clientService.findById();
        System.out.printf("%-10s %-15s %-15s %-15s%n",
                "Client ID", "First Name", "Last Name", "Phone");
        System.out.println("------------------------------------------------------");
        System.out.printf("%-10s %-15s %-15s %-15s%n",
                fetchedClient.getClientId(),
                fetchedClient.getFirstName(),
                fetchedClient.getLastName(),
                fetchedClient.getPhone());
    }

    public void update() {
        Client client = clientService.update();
        System.out.println("Client updated successfully with Id  : " + client.getClientId());
    }

    public void delete() {
        clientService.delete();
        System.out.println("Client deleted successfully");
    }

    public void reservationMenu(ReservationService reservationService, RoomService roomService, ClientService clientService, StatisticsService statisticsService) {
        ReservationMenu reservationMenu = new ReservationMenu(reservationService,clientService,roomService,statisticsService);
        reservationMenu.reservationMenu();
    }

}