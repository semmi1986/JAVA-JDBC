package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    public static Connection getConnection()  {
        try {
            String url = "jdbc:postgresql://localhost/dbtest";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "2067");
            props.setProperty("ssl", "false");
            return DriverManager.getConnection(url, props);

        } catch (SQLException e) {
            throw new RuntimeException();
        }



    }


}
