package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        Connection mySQLConnection = ConnectionUtil.getConnection();
        UserDao userDao = UserDaoJDBCImpl.getInstance();

        userDao.createUsersTable();

        List<User> fourUsers = List.of(
                new User("Ivan", "Mironov", (byte) 35),
                new User("Alex", "Filipov", (byte) 16),
                new User("Olga", "Samoilova", (byte) 44),
                new User("Regina", "Shevchenko", (byte) 21)
            );

        fourUsers.forEach(user -> {
            userDao.saveUser(user.getName(), user.getLastName(), user.getAge());
        });

        userDao.getAllUsers().forEach(System.out::println);

        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
