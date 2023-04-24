package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Logger logger;
    private static final Properties properties = new Properties();
    ;
    private static Connection connection;
    private static final SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    static {
        logger = getConfigureLogger(Util.class.getName());
        configureProperties();
        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private Util() {
    }

    public static Logger getConfigureLogger(String nameClass) {
        try {
            logger = Logger.getLogger(nameClass);
            FileHandler fileHandler = new FileHandler("log_warning.txt", true) {{
                setLevel(Level.WARNING);
                setFormatter(new SimpleFormatter());
            }};
            logger.addHandler(fileHandler);
        } catch (IOException ignore) {
            throw new RuntimeException(ignore);
        }
        return logger;
    }

    private static void configureProperties() {
        try {
            properties.load(Util.class.getClassLoader().getResourceAsStream("application.yaml"));
        } catch (IOException ignore) {
            throw new RuntimeException(ignore);
        }
    }

    public static Connection connect() {
        try {
            connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password"));
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
        return connection;
    }

    public static Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public static void closSessionFactory() {
        if (sessionFactory != null) sessionFactory.close();
    }
}
