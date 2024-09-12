package main.java.repository;

import main.java.config.DatabaseConnection;
import main.java.entities.Client;
import main.java.exception.ClientNotFoundException;
import main.java.exception.ReservationNotFoundException;
import main.java.repository.dao.HotelDao;
import main.java.validators.ClientValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository extends HotelDao<Client> {


    @Override
    public void save(Client client) {
        ClientValidator.validatorClient(client);
        String insert = "INSERT INTO clients (first_name, last_name, phone) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insert);
        ) {
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.executeUpdate();
            System.out.println("Client added successfully");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }


    @Override
    public void update(Client client) {
        String sql = "UPDATE clients SET first_name = ?, last_name = ?, phone = ? WHERE client_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.setLong(4, client.getClientId());
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Client updated successfully");
            } else {
                throw new ReservationNotFoundException("Reservation update failed");
            }
        } catch (SQLException sqlException) {
            System.out.println("SQL Exception: " + sqlException.getMessage());
        }

    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM clients WHERE client_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Client deleted successfully");
            } else {
                throw new ClientNotFoundException("Client delete failed");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public Optional<Client> findById(Long id) {
        String sql = "SELECT * FROM clients WHERE client_id = ?";
        Optional<Client> client = Optional.empty();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Client foundClient = new Client();
                foundClient.setClientId(resultSet.getLong("client_id"));
                foundClient.setFirstName(resultSet.getString("first_name"));
                foundClient.setLastName(resultSet.getString("last_name"));
                foundClient.setPhone(resultSet.getString("phone"));
                client = Optional.ofNullable(foundClient);
            }
        } catch (SQLException sqlException) {
            System.out.println("Error fetching client: " + sqlException.getMessage());
        }
        return client;
    }



    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Client client = new Client();
                client.setClientId(resultSet.getLong(1));
                client.setFirstName(resultSet.getString(2));
                client.setLastName(resultSet.getString(3));
                client.setPhone(resultSet.getString(4));
                clients.add(client);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return clients;
    }
}