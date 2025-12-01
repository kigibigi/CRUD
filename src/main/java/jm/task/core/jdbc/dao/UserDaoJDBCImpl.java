package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    Connection connection =  ConnectionUtil.getConnection();


    public void createUsersTable() {
        final String SQL_CREATE_TABLE = """
                CREATE TABLE users
                (
                    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(30) NOT NULL,
                    last_name VARCHAR(30) NOT NULL,
                    age INT NOT NULL 
                )
                """;

        System.out.println(SQL_CREATE_TABLE);

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_TABLE)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        final String SQL_DROP_TABLE = """
                DROP TABLE users
                """;

        System.out.println(SQL_DROP_TABLE);

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DROP_TABLE)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL_SAVE_USER = """
                INSERT INTO users(name, last_name, age)
                VALUES (?, ?, ?)
                """;

        System.out.println(SQL_SAVE_USER);

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String SQL_DELETE_USER = """
                DELETE FROM users WHERE id=?
                """;

        System.out.println(SQL_DELETE_USER);

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String SQL_GET_ALL_USERS = """
                SELECT * FROM users
                """;

        System.out.println(SQL_GET_ALL_USERS);

        List<User> users = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public void cleanUsersTable() {
        String SQL_CLEAN = """
                TRUNCATE users
                """;

        System.out.println(SQL_CLEAN);

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_CLEAN)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
