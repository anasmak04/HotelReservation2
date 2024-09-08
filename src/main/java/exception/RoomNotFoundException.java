package main.java.exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String message) {
        super("ROOM NOT FOUND: " + message);
    }
}
