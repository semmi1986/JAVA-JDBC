package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService usrService = new UserServiceImpl();

        usrService.createUsersTable();
        usrService.saveUser("Tom", "Tailor", (byte) 18);
        usrService.saveUser("John", "Smith", (byte) 20);
        usrService.saveUser("Bob", "Marli", (byte) 19);
        usrService.saveUser("Juli", "Robinson", (byte) 29);
        usrService.removeUserById(2);
        usrService.dropUsersTable();
    }
}
