package main.java.ui;

import main.java.entities.Client;
import main.java.service.ClientService;

import java.util.Scanner;

public class ClientMenu {

    private final ClientService clientService;
    private Scanner scanner;

    public ClientMenu(ClientService clientService) {
        this.clientService = clientService;
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
        clientService.findAll()
                .forEach(client -> System.out.println("Client : " + client.getFirstName()
                        + client.getLastName() + client.getPhone()));
    }

    public void findById() {
        Client fetchedClient = clientService.findById();
        System.out.println("Client : " + fetchedClient.getFirstName() + fetchedClient.getLastName());
    }

    public void update() {
        Client client = clientService.update();
        System.out.println("Client updated successfully with Id  : " + client.getClientId());
    }

    public void delete() {
         clientService.delete();
        System.out.println("Client deleted successfully");
    }

}
