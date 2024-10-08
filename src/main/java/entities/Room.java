package main.java.entities;


import main.java.enums.RoomType;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private Long roomId;
    private String roomName;
    private RoomType roomType;
    private double basePrice;
    public List<Reservation> reservations = new ArrayList<>();

    public Room() {}

    public Room(Long roomId) {
        this(roomId,null);
    }

    public Room(Long roomId, String roomName) {
        this(roomId,roomName,null);
    }

    public Room(Long roomId, String roomName, RoomType roomType) {
        this(roomId,roomName,roomType,0);
    }

    public Room(Long roomId, String roomName, RoomType roomType, double basePrice) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomType = roomType;
        this.basePrice = basePrice;
    }



    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void deleteReservation(Reservation reservation) {
        this.reservations.remove(reservation);
    }
}
