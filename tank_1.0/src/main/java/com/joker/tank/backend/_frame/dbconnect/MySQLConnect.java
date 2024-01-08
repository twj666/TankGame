package com.joker.tank.backend._frame.dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author 燧枫
 * @date 2022/12/19 9:43
*/
public class MySQLConnect {

    Connection connection;

    public Statement connect() {
        String username = "root";
        String password = "123456";
        String url = "jdbc:mysql://127.0.0.1:3306/tank?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
        String driverName = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, username, password);
            return connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
