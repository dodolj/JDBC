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
        //f логгер
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);

        UserDao userDao = new UserDaoJDBCImpl();

        //а создать таблицу
        userDao.createUsersTable();
        logger.info("Таблица пользователей создана.");

        //b добавить 4 пользователя
        userDao.saveUser("Dima", "Drozdov", (byte) 25);
        userDao.saveUser("Misha", "Zhurikov", (byte) 24);
        userDao.saveUser("Charlie", "Chaplin", (byte) 35);
        userDao.saveUser("Unknown4", "Durak", (byte) 1000);
        logger.info("4 пользователя добавлены в базу данных.");

        //с вывести в консоль
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        //d очистить таблицу
        userDao.cleanUsersTable();
        logger.info("Таблица пользователей очищена.");

        //e удалить таблицу
        userDao.dropUsersTable();
        logger.info("Таблица пользователей удалена.");
    }
}
