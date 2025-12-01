package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        executeUpdateSQL(SQL_CREATE_TABLE);
    }

    public void dropUsersTable() {
        final String SQL_DROP_TABLE = """
                DROP TABLE users
                """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL_DROP_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL_SAVE_USER = String.format("""
                INSERT INTO users(name, last_name, age)
                VALUES ('%s', '%s', %d)
                """,name, lastName, age);

        System.out.println(SQL_SAVE_USER);

        excuteSQL(SQL_SAVE_USER);
    }

    public void removeUserById(long id) {
        String SQL_DELETE_USER = String.format("""
                DELETE FROM users WHERE id=%d
                """, id);

        System.out.println(SQL_DELETE_USER);

        executeUpdateSQL(SQL_DELETE_USER);
    }

    public List<User> getAllUsers() {
        String SQL_GET_ALL_USERS = """
                SELECT * FROM users
                """;

        System.out.println(SQL_GET_ALL_USERS);

        List<User> users = new ArrayList<>();

        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_GET_ALL_USERS);
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

        executeUpdateSQL(SQL_CLEAN);
    }


    private void executeUpdateSQL(String sqlQuery) {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void excuteSQL(String sqlQuery) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
