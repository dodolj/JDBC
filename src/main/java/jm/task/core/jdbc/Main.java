package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDaoJDBCImpl();
        UserServiceImpl userService = new UserServiceImpl(userDao);

        userService.createUsersTable();
        logger.info("Таблица пользователей создана");

        userService.saveUser("Dima", "Drozdov", (byte) 25);
        userService.saveUser("Misha", "Zhurikov", (byte) 24);
        userService.saveUser("Charlie", "Chaplin", (byte) 35);
        userService.saveUser("Count", "Dracula", (byte) 2000);
        logger.info("4 пользователя добавлены в базу данных");

        List<User> users = userService.getAllUsers();
        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        logger.info("Таблица пользователей очищена");

        userService.dropUsersTable();
        logger.info("Таблица пользователей удалена");
    }
}