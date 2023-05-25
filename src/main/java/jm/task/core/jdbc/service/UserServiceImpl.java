package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
  //  private final UserDao userDao;
    UserDao dataBase = new UserDaoHibernateImpl();
//    public UserServiceImpl(UserDao userDao){
//        this.userDao = userDao;
//    }



    public void createUsersTable() {
        dataBase.createUsersTable();
    }

    public void dropUsersTable() {
        dataBase.dropUsersTable();

    }

    public void saveUser(String name, String lastName, byte age) {
        dataBase.saveUser(name, lastName, age);

    }

    public void removeUserById(long id) {
        dataBase.removeUserById(id);
    }

    public List<User> getAllUsers() {

        return dataBase.getAllUsers();
    }

    public void cleanUsersTable() {
        dataBase.cleanUsersTable();

    }
}
