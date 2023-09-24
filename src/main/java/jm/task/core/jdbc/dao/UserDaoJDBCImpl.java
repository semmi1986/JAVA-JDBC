package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.closeConnection;
import static jm.task.core.jdbc.util.Util.openConnection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sqlQuery = "CREATE TABLE Users (id SERIAL PRIMARY KEY, name varchar(255), lastname varchar(255), age smallint);";
        try {
            Statement statement = openConnection().createStatement();
            statement.executeUpdate(sqlQuery);
            closeConnection();
        } catch (SQLException e) {
            System.out.println("Таблица уже существует");
        }
    }

    public void dropUsersTable() {
        String sqlQuery = "DROP TABLE IF EXISTS Users";
        try {
            Statement statement = openConnection().createStatement();
            statement.executeUpdate(sqlQuery);
            System.out.println("Таблица удалена");
            closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlQuery = "INSERT INTO Users (name, lastname, age) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = openConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем %s добавлен в базу\n", name);
            closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sqlQuery = "delete from Users where id=?";
        try {
            PreparedStatement preparedStatement = openConnection().prepareStatement(sqlQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        try {
            Statement statement = openConnection().createStatement();
            String sqlQuery = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                listUser.add(user);
                closeConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listUser;
    }

    public void cleanUsersTable() {
        String sqlQuery = "TRUNCATE TABLE Users";
        try {
            PreparedStatement statement = openConnection().prepareStatement(sqlQuery);
            statement.executeUpdate();
            System.out.println("База данных очищена");
            closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
