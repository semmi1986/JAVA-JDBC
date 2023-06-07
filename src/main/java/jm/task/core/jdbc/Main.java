package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;


public class Main {
    private static final UserService userService = new UserServiceImpl();
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        userService.createUsersTable();
        userService.saveUser("1", "1", (byte) 1);
        userService.saveUser("2", "2", (byte) 2);
        userService.saveUser("3", "3", (byte) 3);
        userService.saveUser("4", "4", (byte) 4);
        List<User> users = userService.getAllUsers();
        for (User user : users){
            System.out.println(user.getName()+" "+user.getLastName()+" "+user.getAge());
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
