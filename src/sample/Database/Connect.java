package sample.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Павел on 17.05.2016.
 */
public class Connect {
    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        Connect.connection = connection;
    }

    public static Connection getDBConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не найден!");
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?autoReconnect=true&useSSL=false", "root", "root");
            return connection;
        } catch (SQLException e) {
            System.out.println("Соединение с БД не установлено!");
        }
        return null;
    }
}
