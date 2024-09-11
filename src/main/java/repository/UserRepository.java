//package main.java.repository;
//
//import main.java.config.DatabaseConnection;
//import main.java.entities.Role;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class UserRepository {
//
//    public void createAccount(User user) {
//        String sql = "INSERT INTO users (email, password, name, phone_number, address, role_id) VALUES (?, ?, ?, ?, ?, ?)";
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, user.getEmail());
//            preparedStatement.setString(2, user.getPassword());
//            preparedStatement.setString(3, user.getName());
//            preparedStatement.setString(4, user.getPhoneNumber());
//            preparedStatement.setString(5, user.getAddress());
//            preparedStatement.setLong(6, user.getRole().getRoleId());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public User findUserByEmail(String email) {
//        String sql = "SELECT u.id, u.email, u.password, u.name, u.phone_number, u.address, r.id AS role_id, r.name AS role_name " +
//                "FROM users u JOIN roles r ON u.role_id = r.id WHERE u.email = ?";
//        User user = null;
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, email);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                user = new User();
//                user.setId(resultSet.getLong("id"));
//                user.setEmail(resultSet.getString("email"));
//                user.setPassword(resultSet.getString("password"));
//                user.setName(resultSet.getString("name"));
//                user.setPhoneNumber(resultSet.getString("phone_number"));
//                user.setAddress(resultSet.getString("address"));
//                Role role = new Role(resultSet.getLong("role_id"), resultSet.getString("role_name"));
//                user.setRole(role);
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return user;
//    }
//
//    public boolean authenticateUser(String email, String password) {
//        User user = findUserByEmail(email);
//        return user != null && user.getPassword().equals(password);
//    }
//}
