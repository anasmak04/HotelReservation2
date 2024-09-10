package main.java.validators;

import main.java.entities.Room;
import main.java.exception.InvalidInputException;

public class RoomValidator {

    public static void validatorRoom(Room room) {
        if(room.getRoomName().trim().isEmpty() || room.getRoomName() == null) {
            throw new InvalidInputException("Room cannot be empty or null");
        }

        if(room.getRoomType().name().trim().isEmpty()) {
            throw new InvalidInputException("Room cannot be empty or null");
        }

        if(room.getPrice() < 0) {
            throw new InvalidInputException("Price cannot be less than 0");
        }

    }
}
