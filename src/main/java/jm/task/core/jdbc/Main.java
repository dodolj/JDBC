package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);

        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();
        logger.info("Таблица пользователей создана.");

        userDao.saveUser("Alice", "Smith", (byte) 25);
        userDao.saveUser("Bob", "Johnson", (byte) 30);
        userDao.saveUser("Charlie", "Brown", (byte) 35);
        userDao.saveUser("David", "Williams", (byte) 40);
        logger.info("4 пользователя добавлены в базу данных.");

        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userDao.cleanUsersTable();
        logger.info("Таблица пользователей очищена.");

        userDao.dropUsersTable();
        logger.info("Таблица пользователей удалена.");
    }
}
