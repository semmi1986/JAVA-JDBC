package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.exceptions.UserDaoException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private static final UserDaoJDBCImpl INSTANCE = new UserDaoJDBCImpl();

    private static final String ADD_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS users (
            id SERIAL PRIMARY KEY ,
            name VARCHAR(255) NOT NULL ,
            lastname VARCHAR(255) NOT NULL ,
            age INT NOT NULL 
            )
            """;
    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS users;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;
    private static final String ADD_USER_SQL = """
            INSERT INTO users (name, lastname, age) 
            VALUES (?,?,?) 
            --ON CONFLICT (name, lastname) DO NOTHING ;
            """;
    private static final String GET_ALL_SQL = """
            SELECT id, 
            name, 
            lastname, 
            age 
            FROM users
            """;
    private static final String CLEAR_ALL_SQL = """
                       TRUNCATE users RESTART IDENTITY; 
            """;

    private UserDaoJDBCImpl() {

    }

    public static UserDaoJDBCImpl getInstance() {
        return INSTANCE;
    }

    public void createUsersTable() {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_TABLE_SQL)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new UserDaoException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UserDaoException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User %s добавлен в таблицу\n", name);
        } catch (SQLException e) {
            throw new UserDaoException(e);
        }

    }

    public void removeUserById(long id) {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UserDaoException(e);
        }

    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> returnedList = new ArrayList<>();
            while (resultSet.next()) {
                User currentUser = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getByte("age")
                );
                currentUser.setId(resultSet.getLong("id"));
                returnedList.add(currentUser);
            }
            return returnedList;
        } catch (SQLException e) {
            throw new UserDaoException(e);
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_ALL_SQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UserDaoException(e);
        }
    }
}
