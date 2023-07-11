package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DEFAULT_DB_URL = "jdbc:postgresql://localhost:5432/db";
    private static final String DEFAULT_HIBERNATE_CONFIG_NAME = "hibernate.cfg.xml";
    private static final SessionFactory sessionFactory;
    private static final Connection connection;

    static {
        final Properties JDBCProperties = new Properties();
        JDBCProperties.setProperty("user", "postgres");
        JDBCProperties.setProperty("password", "1234");
        try {
            connection = DriverManager.getConnection(DEFAULT_DB_URL, JDBCProperties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Configuration configuration = new Configuration().configure();
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        hibernateProperties.setProperty("connection.url", "jdbc:postgresql://localhost:5432/db?useSSL=false");
        hibernateProperties.setProperty("connection.username", "postgres");
        hibernateProperties.setProperty("connection.password", "1234");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        hibernateProperties.setProperty("show_sql", "true");
        hibernateProperties.setProperty("hbm2ddl.auto", "update");
        configuration.setProperties(hibernateProperties);
        sessionFactory = configuration.buildSessionFactory();

    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Connection getConnection() {
        return connection;
    }
}
