package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String URL = "jdbc:postgresql://localhost:5432/my_postgres";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public UserDaoJDBCImpl() {
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void executeUpdate(String sql, String successMessage) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println(successMessage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeUpdateWithParams(String sql, Object... params) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "lastname VARCHAR(100) NOT NULL, " +
                "age INT NOT NULL " +
                ");";
        executeUpdate(sql, "Таблица users успешно создана (если не существовала).\n");
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        executeUpdate(sql, "Таблица users удалена.\n");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";
        executeUpdateWithParams(sql, name, lastName, age);
        System.out.println("Пользователь добавлен: " + name + " " + lastName + "\n");
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        executeUpdateWithParams(sql, id);
        System.out.println("Пользователь с ID " + id + " удален.\n");
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection connection = getConnection();
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
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        executeUpdate(sql, "Таблица users очищена.\n");
    }
}
