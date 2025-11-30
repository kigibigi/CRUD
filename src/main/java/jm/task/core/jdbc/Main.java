package jm.task.core.jdbc;

import jm.task.core.jdbc.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        Connection mySQLConnection = ConnectionUtil.getConnection();
        try {
            System.out.println(mySQLConnection.getTransactionIsolation());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
