package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserService table = new UserServiceImpl();
        table.createUsersTable();

        table.saveUser("Roman", "Gonchar", (byte) 23);
        table.saveUser("Sergey", "Sitnikov", (byte) 23);
        table.saveUser("Kirill", "Lukashin", (byte) 22);
        table.saveUser("Ivan", "Gonchar", (byte) 15);

        List<User> list = table.getAllUsers();
        for (User user : list) {
            System.out.println(user);
        }

        table.removeUserById(3);
        table.cleanUsersTable();
        table.dropUsersTable();
    }
}
