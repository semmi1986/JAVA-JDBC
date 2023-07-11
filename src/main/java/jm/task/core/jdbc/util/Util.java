package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static final String DEFAULT_DB_URL = "jdbc:postgresql://localhost:5432/db";

    public static final Connection CONNECTION = getPostgresConnection(DEFAULT_DB_URL);

    /**
     * Релизует механизм получени соединения с базой данных при помощи JDBC
     *
     * @param url адрес для соединения с БД
     * @return соединение с БД
     */
    public static Connection getPostgresConnection(String url) {
        final Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "1234");
        try {
            return DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
