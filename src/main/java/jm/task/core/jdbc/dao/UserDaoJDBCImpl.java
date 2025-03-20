package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {
    }

    public void executeUpdate(String sql) throws SQLException {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    public void executeUpdate(String sql, Object... params) throws SQLException {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.executeUpdate();
        }
    }

    public void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "lastname VARCHAR(100) NOT NULL, " +
                "age INT NOT NULL " +
                ");";
        try {
            executeUpdate(sql);
            logger.info("Таблица пользователей успешно создана");
        } catch (SQLException e) {
            logger.info(e.getMessage());
            throw e;
        }
    }

    @Override
    public void dropUsersTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS users";
        try {
            executeUpdate(sql);
        } catch (SQLException e) {
            logger.info(e.getMessage());
            throw e;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sql = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";
        try {
            executeUpdate(sql, name, lastName, age);
            System.out.println("Пользователь добавлен: " + name + " " + lastName);
        } catch (SQLException e) {
            logger.info(e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            executeUpdate(sql, id);
            System.out.println("Пользователь с ID " + id + " удален");
        } catch (SQLException e) {
            logger.info(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
            throw e;
        }
        return users;
    }

    @Override
    public void cleanUsersTable() throws SQLException {
        String sql = "TRUNCATE TABLE users";
        try {
            executeUpdate(sql);
            System.out.println("Таблица пользователей очищена");
        } catch (SQLException e) {
            logger.info(e.getMessage());
            throw e;
        }
    }
}