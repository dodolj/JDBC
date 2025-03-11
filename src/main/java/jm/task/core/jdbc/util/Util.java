package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    //открыть connection с БД (в java.SQL.
    //собрать "transactionManager"

    // URL для подключения: jdbc:postgresql://<хост>:<порт>/<название_БД>
    private static final String URL = "jdbc:postgresql://localhost:5432/your_db";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

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
    }
}