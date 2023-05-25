package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
    public static SessionFactory sessionFactory;
    public static final String url = "jdbc:postgresql://localhost/dbtest";
    public static final String username = "postgres";
    public static final String password = "2067";
    public static final String driver_class = "org.postgresql.Driver";

    public static SessionFactory sessionFactory() throws SQLException {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.driver_class", driver_class);
            configuration.setProperty("hibernate.connection.url", url);
            configuration.setProperty("hibernate.connection.username", username);
            configuration.setProperty("hibernate.connection.password", password);
            configuration.addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }

}
