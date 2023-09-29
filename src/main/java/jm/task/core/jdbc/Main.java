package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("first", "firstSurname", (byte) 27);
        userService.saveUser("second", "secondSurname", (byte) 41);
        userService.saveUser("third", "thirdSurname", (byte) 18);
        userService.saveUser("fourth", "fourthSurname", (byte) 35);

        List<User> userList = userService.getAllUsers();
        userList.forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
