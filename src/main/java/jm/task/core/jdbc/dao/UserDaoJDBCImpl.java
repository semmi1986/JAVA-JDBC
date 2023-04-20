package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger logger = Util.getConfigureLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = Util.connect();
             PreparedStatement statement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS users (" +
                             "id BIGSERIAL PRIMARY KEY, " +
                             "name VARCHAR(50) NOT NULL, " +
                             "lastName VARCHAR(50) NOT NULL, " +
                             "age SMALLINT NOT NULL);")) {
            statement.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.connect();
             PreparedStatement statement = connection.prepareStatement(
                "DROP TABLE IF EXISTS users;")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.connect();
             PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.connect();
             PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM users WHERE id = ?;")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.connect();
             PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users;")) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getByte(4)));
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.connect();
             PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM users;")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }
}
