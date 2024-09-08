package main.java.ui;

import main.java.service.ReservationService;

import java.util.Scanner;

public class ReservationMenu {

    private final ReservationService reservationService;
    private final Scanner scanner;

    public ReservationMenu(ReservationService reservationService) {
        this.reservationService = reservationService;
        this.scanner = new Scanner(System.in);
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
            System.out.println("7. Show Clients");
            System.out.println("8. Show Rooms");
            System.out.println("9. Exit");
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
                    findAllDeletedReservations();
                    return;
                case 7:
                    System.out.println("Returning to show clients...");
                    return;
                case 8:
                    System.out.println("Returning to show rooms...");
                    return;
                case 9:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
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
        reservationService.findById();
    }

    public void findAll() {
        reservationService.findAll()
                .forEach(reservation -> System.out.println(
                        "Reservation ID: " + reservation.getReservationId() +
                                ", Start Date: " + reservation.getStartDate() +
                                ", End Date: " + reservation.getEndDate() +
                                ", Room Name: " + (reservation.getRoom().getRoomName()) +
                                ", Client Name: " + (reservation.getClient().getFirstName() + " " + reservation.getClient().getLastName()) +
                                ", Status: " + reservation.getStatus()
                ));
    }

    public void findAllDeletedReservations(){
        reservationService.findAllDeletedReservations()
                .forEach(reservation -> System.out.println(
                        "Reservation ID: " + reservation.getReservationId() +
                                ", Start Date: " + reservation.getStartDate() +
                                ", End Date: " + reservation.getEndDate() +
                                ", Room Name: " + (reservation.getRoom().getRoomName()) +
                                ", Client Name: " + (reservation.getClient().getFirstName() + " " + reservation.getClient().getLastName()) +
                                ", Status: " + reservation.getStatus()
                ));    }

}
