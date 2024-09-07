package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(
                    "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY, " +
                            "name VARCHAR(50)," +
                            "lastname VARCHAR(50)," +
                            "age SMALLINT)");
            query.executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            System.out.println("Ошибка создания таблицы " + e.getMessage());
        }
    }
    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (IllegalArgumentException e) {
            System.out.println("Пользователь с id " + id + " не может быть удален, т.к. не существует. \n" + e.getMessage());
        } catch (HibernateException e) {
            System.out.println("Ошибка удаления пользователя по id " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM User");
            users = query.getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка при попытке достать всех пользователей  " + e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка очистки таблицы  " + e.getMessage());
        }
    }


    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS users");
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка удаления таблицы  " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.saveOrUpdate(user);
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            System.out.println("Ошибка сохранения пользователя " + e.getMessage());
        }
    }
}