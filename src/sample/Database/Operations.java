package sample.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import sample.Objects.Deposit;
import sample.Objects.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Павел on 05.05.2016.
 */
public class Operations {
    private static PreparedStatement preparedStatement;

    public static void addInfo(Connection connection, Deposit deposit) throws SQLException {
        String info="insert into database.deposits(name,time, insertRate ,info,minSum) values(?,?,?,?,?);";
        preparedStatement=connection.prepareStatement(info);
        preparedStatement.setString(1,deposit.getName());
        preparedStatement.setInt(2,deposit.getTime());
        preparedStatement.setDouble(3,deposit.getInsertRate());
        preparedStatement.setString(4,deposit.getInfo());
        preparedStatement.setInt(5,deposit.getMinSum());
        preparedStatement.executeUpdate();
    }

    public static ObservableList<Deposit> getInfo(Connection connection)throws SQLException{
        ArrayList<Deposit> deposits=new ArrayList();
        String info="SELECT name,time, insertRate, info, minSum FROM database.deposits";
        preparedStatement=connection.prepareStatement(info);
        ResultSet rs=preparedStatement.executeQuery();
        while(rs.next()){
            Deposit deposit=new Deposit();
            deposit.setName(rs.getString(1));
            deposit.setTime(rs.getInt(2));
            deposit.setInsertRate(rs.getDouble(3));
            deposit.setInfo(rs.getString(4));
            deposit.setMinSum(rs.getInt(5));
            deposits.add(deposit);
        }
        ObservableList<Deposit> rezult= FXCollections.observableArrayList(deposits);
        return rezult;
    }

    public static void updateInfo(Connection connection,Deposit deposit,Deposit oldDeposit) throws SQLException {
        String info = "UPDATE database.deposits SET name = ?,time=?,insertRate=?,info=?,minSum=? WHERE name=?";
        preparedStatement = connection.prepareStatement(info);
        preparedStatement.setString(1,deposit.getName());
        preparedStatement.setInt(2,deposit.getTime());
        preparedStatement.setDouble(3,deposit.getInsertRate());
        preparedStatement.setString(4,deposit.getInfo());
        preparedStatement.setInt(5,deposit.getMinSum());
        preparedStatement.setString(6,oldDeposit.getName());
        preparedStatement.executeUpdate();
    }

    public static void deleteInfo(Connection connection,Deposit deposit) throws SQLException {
        String info = "DELETE from database.deposits WHERE name =?";
        preparedStatement=connection.prepareStatement(info);
        preparedStatement.setString(1,deposit.getName());
        preparedStatement.executeUpdate();
    }

    public static String checkLogin(Connection connection, User user) throws SQLException{
        String info = "SELECT login, password FROM database.users WHERE login=?";
        String login=null;
        preparedStatement=connection.prepareStatement(info);
        preparedStatement.setString(1,user.getLogin());
        ResultSet rs=preparedStatement.executeQuery();
        while (rs.next()) {
            login = rs.getString("login");
        }
        return login;
    }

    public static ArrayList<User> getUsers(Connection connection)throws SQLException{
        ArrayList<User> users=new ArrayList();
        String info="SELECT login, password FROM database.users";
        preparedStatement=connection.prepareStatement(info);
        ResultSet rs=preparedStatement.executeQuery();
        while(rs.next()){
            User user=new User();
            user.setLogin(rs.getString(1));
            user.setPassword(rs.getString(2));
            users.add(user);
        }
        return users;
    }

    public static void addUser(Connection connection, User user) throws SQLException {
        String info="insert into database.users(login,password) values(?,?);";
        preparedStatement=connection.prepareStatement(info);
        preparedStatement.setString(1,user.getLogin());
        preparedStatement.setString(2,user.getPassword());
        preparedStatement.executeUpdate();
    }

}
