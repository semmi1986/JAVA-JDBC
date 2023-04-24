package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = Util.getConfigureLogger(UserDaoHibernateImpl.class.getName());
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id BIGSERIAL PRIMARY KEY, " +
                            "name VARCHAR(50) NOT NULL, " +
                            "lastName VARCHAR(50) NOT NULL, " +
                            "age SMALLINT NOT NULL);"
            );
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            assert transaction != null;
            transaction.rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(
                    "DROP TABLE IF EXISTS users;"
            ).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            assert transaction != null;
            transaction.rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            assert transaction != null;
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            assert transaction != null;
            transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            list = session.createQuery("SELECT u FROM User u", User.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            assert transaction != null;
            transaction.rollback();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.createQuery(
                    "DELETE FROM User"
            ).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            assert transaction != null;
            transaction.rollback();
        }
    }
}
