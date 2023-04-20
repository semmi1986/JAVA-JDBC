package jm.task.core.jdbc.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.*;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Logger logger;
    private static final Properties properties = new Properties();;
    private static Connection connection;

    static {
         logger = getConfigureLogger(Util.class.getName());
        configureProperties();
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
        if (connection == null) {
            initConnection();
        }
        return connection;
    }

    private static void initConnection() {
        try {
            Class.forName(properties.getProperty("driver"));
            connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password"));
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
    }
}
