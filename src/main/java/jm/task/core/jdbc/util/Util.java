package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final Logger logger = Logger.getLogger(Util.class.getName());
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static Connection connection;
    public void connect(){
        try {
            Class.forName("org.postgresql.Driver");             //.getDeclaredConstructor().newInstance()
            connection = DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
            logger.log(Level.INFO,"Connection successful");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Connection failed...", ex);
        }
    }
    public Connection getConnection(){
        if(connection == null){
            connect();
        }
        return connection;
    }

}
