package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import java.util.List;

import static jm.task.core.jdbc.dao.UserDaoJDBCImpl.*;

public class UserServiceImpl implements UserService {
    public void createUsersTable() {
        getInstance().createUsersTable();
    }

    public void dropUsersTable() {
        getInstance().dropUsersTable();

    }

    public void saveUser(String name, String lastName, byte age) {
        getInstance().saveUser(name, lastName, age);

    }

    public void removeUserById(long id) {
        getInstance().removeUserById(id);
    }

    public List<User> getAllUsers() {
        return getInstance().getAllUsers();
    }

    public void cleanUsersTable() {
        getInstance().cleanUsersTable();
    }
}
