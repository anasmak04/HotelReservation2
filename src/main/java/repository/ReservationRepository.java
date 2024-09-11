package main.java.repository;

import main.java.config.DatabaseConnection;
import main.java.entities.*;
import main.java.enums.ReservationStatus;
import main.java.exception.ReservationNotFoundException;
import main.java.exception.RoomNotFoundException;
import main.java.repository.dao.HotelDao;
import main.java.service.DynamicPricingService;
import main.java.service.RoomService;
import main.java.validators.ReservationValidator;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationRepository extends HotelDao<Reservation> {
    private final RoomService roomService;
    private final ClientRepository clientRepository;
    private final RoomRepository roomRepository;
    private final DynamicPricingService dynamicPricingService;
    private final ReservationValidator reservationValidator;

    public ReservationRepository(RoomService roomService, ClientRepository clientRepository, RoomRepository roomRepository, DynamicPricingService dynamicPricingService, ReservationValidator reservationValidator) {
        this.roomService = roomService;
        this.clientRepository = clientRepository;
        this.roomRepository = roomRepository;
        this.dynamicPricingService = dynamicPricingService;
        this.reservationValidator = reservationValidator;
    }


    @Override
    public void save(Reservation reservation) {
        reservationValidator.validatorReservation(reservation);

        String sql = "INSERT INTO reservations (start_date, end_date, room_id, client_id, status, totalprice) VALUES (?, ?, ?, ?, ?::ReservationStatus, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            Date startDate = Date.valueOf(reservation.getStartDate());
            Date endDate = Date.valueOf(reservation.getEndDate());

            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);
            preparedStatement.setLong(3, reservation.getRoom().getRoomId());
            preparedStatement.setLong(4, reservation.getClient().getClientId());
            preparedStatement.setString(5, reservation.getStatus().toString());

            double pricing = dynamicPricingService.calculateFinalPrice(reservation);
            reservation.setTotalPrice(pricing);
            preparedStatement.setBigDecimal(6, BigDecimal.valueOf(reservation.getTotalPrice()));


            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                reservation.getRoom().addReservation(reservation);
                reservation.getClient().addReservation(reservation);
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
        String sql = "SELECT\n" +
                "    r.reservation_id AS reservation_id,\n" +
                "    r.start_date,\n" +
                "    r.end_date,\n" +
                "    r.status AS reservation_status,\n" +
                "    r.totalprice,\n" +
                "    rm.room_id AS room_id,\n" +
                "    rm.room_name,\n" +
                "    rm.price AS room_price,\n" +
                "    cl.client_id AS client_id,\n" +
                "    cl.first_name AS client_first_name,\n" +
                "    cl.last_name AS client_last_name \n" +
                "FROM\n" +
                "    reservations r\n" +
                "JOIN\n" +
                "    rooms rm ON r.room_id = rm.room_id \n" +
                "JOIN\n" +
                "    clients cl ON r.client_id = cl.client_id ;\n";

        List<Reservation> reservations = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationId(resultSet.getLong("reservation_id"));
                reservation.setStartDate(resultSet.getDate("start_date").toLocalDate());
                reservation.setEndDate(resultSet.getDate("end_date").toLocalDate());
                reservation.setStatus(ReservationStatus.valueOf(resultSet.getString("reservation_status")));
                BigDecimal totalPriceBD = resultSet.getBigDecimal("totalprice");
                double totalPrice = (totalPriceBD != null) ? totalPriceBD.doubleValue() : 0.0;
                reservation.setTotalPrice(totalPrice);

                Room room = new Room();
                room.setRoomId(resultSet.getLong("room_id"));
                room.setRoomName(resultSet.getString("room_name"));
                room.setBasePrice(resultSet.getDouble("room_price"));

                Client client = new Client();
                client.setClientId(resultSet.getLong("client_id"));
                client.setFirstName(resultSet.getString("client_first_name"));
                client.setLastName(resultSet.getString("client_last_name"));

                reservation.setRoom(room);
                reservation.setClient(client);

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
            Optional<Reservation> reservation = findById(id);
            if(reservation.isPresent()){
                Reservation reservation1 = reservation.get();

                if(reservation1.getRoom() != null){
                    reservation1.getRoom().deleteReservation(reservation1);
                }

                if(reservation1.getClient() != null){
                    reservation1.getClient().removeReservation(reservation1);
                }
            }

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
    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT\n" +
                "    r.reservation_id AS reservation_id,\n" +
                "    r.start_date,\n" +
                "    r.end_date,\n" +
                "    r.status AS reservation_status,\n" +
                "    r.totalprice,\n" +
                "    rm.room_id AS room_id,\n" +
                "    rm.room_name AS room_name,\n" +
                "    rm.price AS room_price,\n" +
                "    cl.client_id AS client_id,\n" +
                "    cl.first_name AS client_first_name,\n" +
                "    cl.last_name AS client_last_name\n" +
                "FROM\n" +
                "    reservations r\n" +
                "JOIN\n" +
                "    rooms rm ON r.room_id = rm.room_id\n" +
                "JOIN\n" +
                "    clients cl ON r.client_id = cl.client_id\n" +
                "WHERE\n" +
                "    r.reservation_id = ?";

        Optional<Reservation> reservation = Optional.empty();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Reservation res = new Reservation();
                res.setReservationId(resultSet.getLong("reservation_id"));
                res.setStartDate(resultSet.getDate("start_date").toLocalDate());
                res.setEndDate(resultSet.getDate("end_date").toLocalDate());

                Room room = new Room();
                room.setRoomId(resultSet.getLong("room_id"));
                room.setRoomName(resultSet.getString("room_name"));
                room.setBasePrice(resultSet.getDouble("room_price"));
                res.setRoom(room);

                Client client = new Client();
                client.setClientId(resultSet.getLong("client_id"));
                client.setFirstName(resultSet.getString("client_first_name"));
                client.setLastName(resultSet.getString("client_last_name"));
                res.setClient(client);

                res.setStatus(ReservationStatus.valueOf(resultSet.getString("reservation_status")));
                res.setTotalPrice(resultSet.getBigDecimal("totalprice").doubleValue());

                reservation = Optional.of(res);
            }

        } catch (SQLException sqlException) {
            System.out.println("Error fetching reservation: " + sqlException.getMessage());
        }

        return reservation;
    }

}