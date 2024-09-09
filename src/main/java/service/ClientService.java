package main.java.service;

import main.java.entities.Client;
import main.java.repository.ClientRepository;

import java.util.List;
import java.util.Scanner;

public class ClientService  {

    private final ClientRepository clientRepository;
    private final Scanner scanner;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        this.scanner = new Scanner(System.in);
    }

    public Client save() {
        System.out.println("Enter Client first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter Client last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter Client phone: ");
        String phone = scanner.nextLine();
        Client client = new Client(null, firstName, lastName, phone);
        return clientRepository.save(client);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById() {
        System.out.println("Enter Client Id to delete: ");
        Long id = scanner.nextLong();
        return clientRepository.findById(id);
    }

    public Client update() {
        System.out.print("Enter client ID: ");
        Long id = scanner.nextLong();
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        Client client = new Client(id, firstName, lastName, phone);
        return clientRepository.save(client);
    }

    public void delete() {
        System.out.println("Enter Client Id to delete: ");
        Long id = scanner.nextLong();
        clientRepository.delete(id);
        System.out.println("Client deleted successfully: ");
    }


}