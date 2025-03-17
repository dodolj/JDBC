package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;
import java.util.logging.Logger;

    //привести код в порядок
    //залить на гит "говнокода"
    //почитать про Lombok + подключить
    //настроись совместимость ломбка с мавеном
    //UUID

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();
        logger.info("Таблица пользователей создана.");

        userDao.saveUser("Dima", "Drozdov", (byte) 25);
        userDao.saveUser("Misha", "Zhurikov", (byte) 24);
        userDao.saveUser("Charlie", "Chaplin", (byte) 35);
        userDao.saveUser("Count", "Dracula", (byte) 2000);
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
