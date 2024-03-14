package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

//public class UserDaoHibernateImpl implements UserDao {
//    private static final String ADD_TABLE_SQL = """
//            CREATE TABLE IF NOT EXISTS users  (
//            id SERIAL PRIMARY KEY ,
//            name VARCHAR(255) NOT NULL ,
//            lastname VARCHAR(255) NOT NULL ,
//            age INT NOT NULL )
//            """;
//    private static final String DROP_TABLE_SQL = """
//            DROP TABLE IF EXISTS users
//            """;
//
//    public UserDaoHibernateImpl() {
//
//    }
//
//
//    @Override
//    public void createUsersTable() {
//        try (SessionFactory sessionFactory = Util.getConfig().buildSessionFactory();
//             Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            Query query = session.createNativeQuery(ADD_TABLE_SQL);
//            query.executeUpdate();
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            throw new UserDaoException(e);
//        }
//    }
//
//    @Override
//    public void dropUsersTable() {
//        try (SessionFactory sessionFactory = Util.getConfig().buildSessionFactory();
//             Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            Query query = session.createNativeQuery(DROP_TABLE_SQL);
//            query.executeUpdate();
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            throw new UserDaoException(e);
//        }
//    }
//
//    @Override
//    public void saveUser(String name, String lastName, byte age) {
//        try (SessionFactory sessionFactory = Util.getConfig().buildSessionFactory();
//             Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            User user = new User(name, lastName, age);
//            session.save(user);
//            System.out.printf("User %s добавлен в таблицу\n", name);
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            throw new UserDaoException(e);
//        }
//    }
//
//    @Override
//    public void removeUserById(long id) {
//        try (SessionFactory sessionFactory = Util.getConfig().buildSessionFactory();
//             Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            User user = session.get(User.class, id);
//            if (user != null)
//                session.delete(user);
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            throw new UserDaoException(e);
//        }
//    }
//
//
//    @Override
//    public List<User> getAllUsers() {
//        try (SessionFactory sessionFactory = Util.getConfig().buildSessionFactory();
//             Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            Query<User> query = session.createQuery("FROM User", User.class);
//            return query.list();
//        } catch (Exception e) {
//            throw new UserDaoException(e);
//        }
//    }
//
//    @Override
//    public void cleanUsersTable() {
//        try (SessionFactory sessionFactory = Util.getConfig().buildSessionFactory();
//             Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            Query query = session.createQuery(" delete from User");
//            query.executeUpdate();
//        } catch (Exception e) {
//            throw new UserDaoException(e);
//        }
//    }
//}
