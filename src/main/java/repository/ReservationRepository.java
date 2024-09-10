package main.java.repository;

import main.java.connection.DatabaseConnection;
import main.java.entities.*;
import main.java.enums.ReservationStatus;
import main.java.exception.ReservationNotFoundException;
import main.java.exception.RoomNotFoundException;
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
    private final ClientRepository clientRepository;
    private final RoomRepository roomRepository;

    public ReservationRepository(RoomService roomService, ClientRepository clientRepository, RoomRepository roomRepository) {
        this.roomService = roomService;
        this.clientRepository = clientRepository;
        this.roomRepository = roomRepository;
    }


    @Override
    public void save(Reservation reservation) {

        if (reservation.getEndDate().isBefore(reservation.getStartDate())) {
            System.out.println("Error: End date cannot be before start date.");
        }

        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            System.out.println("Error: Start date cannot be after end date.");
        }

        if (!roomService.isRoomAvailable(reservation.getRoom().getRoomId(), reservation.getStartDate(), reservation.getEndDate())) {
            System.out.println("Error: The room is already reserved for the given dates. Please choose other dates.");
        }

        String sql = "INSERT INTO reservations (start_date, end_date, room_id, client_id, status) VALUES (?, ?, ?, ?, ?::status)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            Date startDate = Date.valueOf(reservation.getStartDate());
            Date endDate = Date.valueOf(reservation.getEndDate());

            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);
            preparedStatement.setLong(3, reservation.getRoom().getRoomId());
            preparedStatement.setLong(4, reservation.getClient().getClientId());
            preparedStatement.setString(5, reservation.getStatus().toString());
            reservation.getRoom().addReservation(reservation);
            reservation.getClient().addReservation(reservation);

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Reservation successfully added.");
            } else {
                throw new ReservationNotFoundException("Reservation Inserted Failed");
            }


        } catch (SQLException e) {
            System.out.println("Error saving reservation: " + e.getMessage());
        }


    }

    @Override
    public void update(Reservation reservation) {
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

            int result = preparedStatement.executeUpdate();

            if (result == 1) {
                System.out.println("Reservation updated successfully ");
            } else {
                throw new ReservationNotFoundException("Reservation update failed");
            }

        } catch (SQLException e) {
            System.out.println("Error updating reservation: " + e.getMessage());
        }

    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservations";
        List<Reservation> reservations = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationId(resultSet.getLong("reservation_id"));
                reservation.setStartDate(resultSet.getDate("start_date").toLocalDate());
                reservation.setEndDate(resultSet.getDate("end_date").toLocalDate());

                Long roomId = resultSet.getLong("room_id");
                Long clientId = resultSet.getLong("client_id");

                Room room = roomRepository.findById(roomId);
                Client client = clientRepository.findById(clientId);

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
            Reservation reservation = findById(id);
            reservation.getRoom().deleteReservation(reservation);
            reservation.getClient().removeReservation(reservation);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Reservation deleted successfully.");
            } else {
                throw new RoomNotFoundException("Reservation delete failed");
            }

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
                Room room = roomRepository.findById(resultSet.getLong("room_id"));
                Client client = clientRepository.findById(resultSet.getLong("client_id"));

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