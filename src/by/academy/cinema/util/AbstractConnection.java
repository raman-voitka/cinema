package by.academy.cinema.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractConnection {

    private static final String url = "jdbc:mysql://127.0.0.1:3307/cinema";
    private static final String username = "root";
    private static final String password = "root";
    private static final String driver = "com.mysql.jdbc.Driver";
    private static Connection connection = null;

    public static Connection connect() throws SQLException {
        try {
            Class.forName(driver);
            if (connection == null) {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Connection Failed", e);
        }
        return connection;
    }

    public static void close() throws SQLException {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                throw new SQLException();
            }
        }
    }
}