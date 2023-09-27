package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.*;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sqlQuery = "CREATE TABLE Users (id SERIAL PRIMARY KEY, name varchar(255), lastname varchar(255), age smallint);";
        try (Statement statement = openConnection().createStatement()) {
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            System.out.println("Таблица уже существует");
        }

    }

    public void dropUsersTable() {
        String sqlQuery = "DROP TABLE IF EXISTS Users";
        try (PreparedStatement preparedStatement = openConnection().prepareStatement(sqlQuery)) {
            Statement statement = openConnection().createStatement();
            statement.executeUpdate(sqlQuery);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlQuery = "INSERT INTO Users (name, lastname, age) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = openConnection().prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем %s добавлен в базу\n", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sqlQuery = "delete from Users where id=?";
        try (PreparedStatement preparedStatement = openConnection().prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        try (Statement statement = openConnection().createStatement()) {
            String sqlQuery = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                listUser.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listUser;
    }

    public void cleanUsersTable() {
        String sqlQuery = "TRUNCATE TABLE Users";
        try (PreparedStatement statement = openConnection().prepareStatement(sqlQuery)) {
            statement.executeUpdate();
            System.out.println("База данных очищена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
