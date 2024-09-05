package main.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Room {
    private Long roomId;
    private String roomName;
    private RoomType roomType;
    private double price;
}
