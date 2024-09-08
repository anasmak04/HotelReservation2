package main.java.repository;

import main.java.connection.DatabaseConnection;
import main.java.entities.Room;
import main.java.entities.RoomType;
import main.java.repository.dao.HotelDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements HotelDao<Room> {

    List<Room> rooms ;
    public RoomRepository() {
        rooms = new ArrayList<>();
    }

    @Override
    public Room save(Room room) {
        String sql = "INSERT INTO rooms (room_name, room_type, price) VALUES (?, ?::roomtype, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, room.getRoomName());
            preparedStatement.setString(2, room.getRoomType().name());
            preparedStatement.setDouble(3, room.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return room;
    }

    @Override
    public Room update(Room room) {
        String sql = "UPDATE rooms SET room_name = ?, room_type = ?, price = ? WHERE room_id = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, room.getRoomName());
            preparedStatement.setString(2, room.getRoomType().name());
            preparedStatement.setDouble(3, room.getPrice());
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return room;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM rooms WHERE room_id = ?";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }


    }

    @Override
    public Room findById(Long id) {
        String sql = "SELECT * FROM rooms WHERE room_id = ?";
        Room room = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                room = new Room();
                room.setRoomId(resultSet.getLong("room_id"));
                room.setRoomName(resultSet.getString("room_name"));
                room.setRoomType(RoomType.valueOf(resultSet.getString("room_type")));
                room.setPrice(resultSet.getDouble("price"));
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return room;
    }

    @Override
    public List<Room> findAll() {
        String sql = "SELECT * FROM rooms";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("room_id");
                String roomName = rs.getString("room_name");
                String roomType = rs.getString("room_type");
                RoomType roomType1 = RoomType.valueOf(roomType);
                double price = rs.getDouble("price");
                Room room = new Room(id,roomName,roomType1,price);
                room.setRoomId(room.getRoomId());
                room.setRoomName(room.getRoomName());
                room.setRoomType(room.getRoomType());
                rooms.add(room);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return rooms;
    }
}
