package main.java.repository;

import main.java.connection.DatabaseConnection;
import main.java.entities.Client;
import main.java.entities.Reservation;
import main.java.entities.ReservationStatus;
import main.java.entities.Room;
import main.java.repository.dao.HotelDao;
import main.java.service.RoomService;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository extends HotelDao<Reservation> {
        private final RoomService roomService;

    public ReservationRepository(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public Reservation save(Reservation reservation) {

        if (reservation.getEndDate().isBefore(reservation.getStartDate())) {
            System.out.println("Error: End date cannot be before start date.");
            return null;
        }

        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            System.out.println("Error: Start date cannot be after end date.");
            return null;
        }

        if (!roomService.isRoomAvailable(reservation.getRoom().getRoomId(), reservation.getStartDate(), reservation.getEndDate())) {
            System.out.println("Error: The room is already reserved for the given dates. Please choose other dates.");
            return null;
        }

        String sql = "INSERT INTO reservations (start_date, end_date, room_id, client_id, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            Date startDate = Date.valueOf(reservation.getStartDate());
            Date endDate = Date.valueOf(reservation.getEndDate());

            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);
            preparedStatement.setLong(3, reservation.getRoom().getRoomId());
            preparedStatement.setLong(4, reservation.getClient().getClientId());
            preparedStatement.setString(5, reservation.getStatus().toString());
            preparedStatement.executeUpdate();

            reservation.getRoom().addReservation(reservation);
            reservation.getClient().addReservation(reservation);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error saving reservation: " + e.getMessage());
        }

        return reservation;
    }

    @Override
    public Reservation update(Reservation reservation) {
        String sql = "UPDATE reservations SET start_date = ?, end_date = ?, room_id = ?, client_id = ?, status = ? WHERE reservation_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            Date startDate = Date.valueOf(reservation.getStartDate());
            Date endDate = Date.valueOf(reservation.getEndDate());

            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);
            preparedStatement.setLong(3, reservation.getRoom().getRoomId());
            preparedStatement.setLong(4, reservation.getClient().getClientId());
            preparedStatement.setString(5, reservation.getStatus().toString());
            preparedStatement.setLong(6, reservation.getReservationId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating reservation: " + e.getMessage());
        }

        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservations";
        List<Reservation> reservations = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationId(resultSet.getLong("reservation_id"));
                reservation.setStartDate(resultSet.getDate("start_date").toLocalDate());
                reservation.setEndDate(resultSet.getDate("end_date").toLocalDate());
                Room room = new Room(resultSet.getLong("room_id"));
                Client client = new Client(resultSet.getLong("client_id"));
                reservation.setRoom(room);
                reservation.setClient(client);
                reservation.setStatus(ReservationStatus.valueOf(resultSet.getString("status")));
                reservations.add(reservation);
            }

        } catch (SQLException sqlException) {
            System.out.println("Error fetching reservations: " + sqlException.getMessage());
        }

        return reservations;
    }

    @Override
    public void delete(Long id) {
        String sql = "UPDATE reservations SET status = ? WHERE reservation_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, ReservationStatus.CANCELLED.name());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();

            Reservation reservation = new Reservation();
            reservation.getRoom().deleteReservation(reservation);
            reservation.getClient().removeReservation(reservation);

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public Reservation findById(Long id) {
        String sql = "SELECT * FROM reservations WHERE reservation_id = ?";
        Reservation reservation = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                reservation = new Reservation();
                reservation.setReservationId(resultSet.getLong("reservation_id"));
                reservation.setStartDate(resultSet.getDate("start_date").toLocalDate());
                reservation.setEndDate(resultSet.getDate("end_date").toLocalDate());
                Room room = new Room(resultSet.getLong("room_id"));
                Client client = new Client(resultSet.getLong("client_id"));
                reservation.setRoom(room);
                reservation.setClient(client);
                reservation.setStatus(ReservationStatus.valueOf(resultSet.getString("status")));
            }

        } catch (SQLException sqlException) {
            System.out.println("Error fetching reservation: " + sqlException.getMessage());
        }

        return reservation;
    }
}
