package jm.task.core.jdbc.util;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Util {
    private static final Logger logger = Logger.getLogger(Util.class.getName());
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    @Getter
    private static SessionFactory sessionFactory;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url", URL);
            configuration.setProperty("hibernate.connection.username", USERNAME);
            configuration.setProperty("hibernate.connection.password", PASSWORD);
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable e) {
            logger.severe("Ошибка при инициализации SessionFactory: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            logger.info("Ошибка подключения к БД");
            throw new RuntimeException(e);
        }
    }
}