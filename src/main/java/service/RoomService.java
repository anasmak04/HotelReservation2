package main.java.service;



import main.java.entities.Room;
import main.java.entities.RoomType;
import main.java.exception.RoomNotFoundException;
import main.java.repository.RoomRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class RoomService {
    private final RoomRepository roomRepository;
    private final Scanner scanner;

    public RoomService(RoomRepository roomRepository) {
        this.scanner = new Scanner(System.in);
        this.roomRepository = roomRepository;
    }

    public Room save() {
        System.out.println("Enter room name: ");
        String roomName = scanner.nextLine();
        System.out.println("Enter room type ('SINGLE', 'DELUXE', 'STUDIO')");
        String roomType = scanner.nextLine();
        RoomType roomType1 = RoomType.valueOf(roomType);
        System.out.println("Enter room price: ");
        double roomPrice = scanner.nextDouble();
        scanner.nextLine();
        Room room = new Room(null, roomName, roomType1, roomPrice);
        return this.roomRepository.save(room);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById() {
        System.out.println("Enter room id: ");
        Long roomId = scanner.nextLong();
        Room room = roomRepository.findById(roomId);
        if (room != null) {
            return room;
        }
        throw new RoomNotFoundException("Room not found for ID: " + roomId);
    }

    public Room update() {
        System.out.println("Enter room Id: ");
        Long roomId = scanner.nextLong();
        scanner.nextLine();
        Room fetchedRoom;
        try{
            fetchedRoom = roomRepository.findById(roomId);
        }catch (RoomNotFoundException roomNotFoundException) {
            System.out.println(roomNotFoundException.getMessage());
        }
        System.out.println("Enter new room name: ");
        String roomName = scanner.nextLine();
        System.out.println("Enter new room type ('SINGLE', 'DELUXE', 'STUDIO')");
        String roomType = scanner.nextLine();
        RoomType roomType1=  RoomType.valueOf(roomType);
        System.out.println("Enter new room price: ");
        double roomPrice = scanner.nextDouble();
        scanner.nextLine();
        Room updatedRoom = new Room(roomId, roomName, roomType1, roomPrice);
        return roomRepository.update(updatedRoom);
    }

    public void delete() {
        System.out.println("Enter room Id: ");
        Long roomId = scanner.nextLong();
        scanner.nextLine();
        roomRepository.delete(roomId);
    }

    public Boolean isRoomAvailable(Long roomId, LocalDate startDate, LocalDate endDate) {
        Room room = roomRepository.findById(roomId);
        return room.getReservations().stream()
                .allMatch(reservation ->
                        (endDate.isBefore(reservation.getStartDate())
                                || startDate.isAfter(reservation.getEndDate()))
                );
    }

}
