package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
/*
        UserDaoJDBCImpl userDaoJDBC = UserDaoJDBCImpl.getInstance();

        userDaoJDBC.createUsersTable();

        userDaoJDBC.saveUser("Vasiliy", "Vasiliev", (byte) 43);
        userDaoJDBC.saveUser("Igor", "Igorkov", (byte) 34);
        userDaoJDBC.saveUser("Semen", "Semenov", (byte) 22);
        userDaoJDBC.saveUser("Svetlana", "Svetlanova", (byte) 76);

        List<User> userList = userDaoJDBC.getAllUsers();
        userList.forEach(System.out::println);
        
        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
*/

        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.createUsersTable();
        userDaoHibernate.saveUser("Semen", "Semenov", (byte) 22);
        userDaoHibernate.saveUser("Vladislav", "Drakula", (byte) 122);
        userDaoHibernate.saveUser("Oleg", "Ivanov", (byte) 42);
        userDaoHibernate.saveUser("Ivan", "Olegov", (byte) 12);
        userDaoHibernate.removeUserById(1);
        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();


    }
}
