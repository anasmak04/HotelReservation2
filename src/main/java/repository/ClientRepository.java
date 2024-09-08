package main.java.repository;

import main.java.connection.DatabaseConnection;
import main.java.entities.Client;
import main.java.repository.dao.HotelDao;

import javax.xml.crypto.Data;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements HotelDao<Client> {

    private final List<Client> clients;

    public ClientRepository() {
        this.clients = new ArrayList<>();
    }


    @Override
    public Client save(Client client) {
        String insert = "INSERT INTO clients (first_name, last_name, phone) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insert);
        ) {
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return client;
    }

    @Override
    public Client update(Client client) {
        String sql = "Update clients set first_name = ?, last_name = ?, phone = ? where clientId = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.setLong(4, client.getClientId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return client;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM clients WHERE clientId = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public Client findById(Long id) {
        String sql = "SELECT * FROM clients WHERE client_id = ?";
        Client client = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    client = new Client();

                    client.setClientId(rs.getLong("client_id"));
                    client.setFirstName(rs.getString("first_name"));
                    client.setLastName(rs.getString("last_name"));
                    client.setPhone(rs.getString("phone"));
                }
            }

        } catch (SQLException sqlException) {
            System.out.println("Error fetching client: " + sqlException.getMessage());
        }

        return client;
    }

    @Override
    public List<Client> findAll() {
        String sql = "SELECT * FROM clients";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long clientId = rs.getLong(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String phone = rs.getString(4);

                Client client = new Client();
                client.setClientId(clientId);
                client.setFirstName(firstName);
                client.setLastName(lastName);
                client.setPhone(phone);
                clients.add(client);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return clients;
    }
}
