package main.java.connection;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {

    public DbConfig() {
        try {
            Class.forName("org.postgresql.Driver");

            String DATABASE_URL = System.getenv("DATABASE_URL");
            String USER = "postgres";
            String PASSWORD = System.getenv("PASSWORD");
            try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
                if (connection != null) {
                    System.out.println("Connection Established");

                    Flyway flyway = Flyway.configure()
                            .dataSource(DATABASE_URL, USER, PASSWORD)
                            .locations("db/migration/V1__initial_tables.sql")
                            .load();

                    flyway.migrate();
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found: " + e.getMessage());
        } catch (SQLException sqlException) {
            System.out.println("Connection error: " + sqlException.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}