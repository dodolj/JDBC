package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // URL для подключения: jdbc:postgresql://<хост>:<порт>/<название_БД>
    /*private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }*/
}