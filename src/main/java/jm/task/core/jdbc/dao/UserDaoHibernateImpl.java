package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String createQuery = """
                    DROP TABLE IF EXISTS users;
                    create table users
                    (
                        id        bigserial       not null,
                        name      varchar(255) not null,
                        last_name varchar(255) not null,
                        age       integer      not null,
                        primary key (id)
                    );
                        
                    alter table users
                        owner to postgres;""";
        session.createSQLQuery(createQuery).executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        final User user = new User(name, lastName, age);
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String deleteQuery = "DELETE FROM users WHERE id = :userId";
        session.createNativeQuery(deleteQuery)
                .setParameter("userId", id)
                .executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<User> users = session.createNativeQuery("SELECT * FROM users", User.class).getResultList();

        transaction.commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();

        transaction.commit();
        session.close();
    }
}
