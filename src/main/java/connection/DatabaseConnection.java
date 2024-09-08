package main.java.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DATABASE_URL = System.getenv("DATABASE_URL");
    private static final String USER = System.getenv("USERNAME");
    private static final String PASSWORD = System.getenv("PASSWORD");
    private static Connection connection;


    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

//            Flyway flyway = Flyway.configure()
//                    .dataSource(DATABASE_URL, USER, PASSWORD)
//                    .load();
//            flyway.migrate();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }


        return connection;
    }
}