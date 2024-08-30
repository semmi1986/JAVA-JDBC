package jm.task.core.jdbc.dao;

import com.mysql.cj.protocol.Resultset;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = Util.getConection();
    private int executeUpdate(String query)  {
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        }
        catch( SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public UserDaoJDBCImpl() {

    }
    public void createUsersTable() {
        String createQuery = " CREATE TABLE IF NOT EXISTS userTable" +
                "(id SERIAL PRIMARY KEY NOT NULL ,"+" name VARCHAR(50),"+ "lastname VARCHAR(50),"+ "age INT)";
        executeUpdate(createQuery);
        System.out.println("Таблица успешно создана");
    }
    public void dropUsersTable() {
        String dropQuery="DROP TABLE IF EXISTS userTable";
        executeUpdate(dropQuery);
        System.out.println("Таблица успешно удалена");
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveQuery="INSERT INTO userTable(name, lastname, age) VALUES (?,?,?)";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(saveQuery);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3,age);
            preparedStatement.executeUpdate();
            System.out.println("User с имененем "+ name+" добавлен в базу данных");
        }
        catch( SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeUserById(long id) {
        String removeQuery="DELETE FROM userTable WHERE id = "+id;
        executeUpdate(removeQuery);
        System.out.println("Пользователь с Id "+id+" успешно удален");
    }

    public List<User> getAllUsers() {
        List<User> allUser = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String getAllQuery = " SELECT * FROM userTable";
            ResultSet resultset = statement.executeQuery(getAllQuery);
            while (resultset.next()) {
                User user = new User();
                user.setId(resultset.getLong("id"));
                user.setName(resultset.getString("name"));
                user.setLastName(resultset.getString("lastname"));
                user.setAge(resultset.getByte("age"));
                allUser.add(user);
            }
            for (User user : allUser) {
                System.out.println(user.toString() + "\n");
            }
        }
        catch( SQLException e){
            e.printStackTrace();
        }
        return allUser;
    }
    public void cleanUsersTable() {
        String cleanQuery ="DELETE FROM userTable";
        executeUpdate(cleanQuery);
        System.out.println("Таблица успешно очищена");
    }
}