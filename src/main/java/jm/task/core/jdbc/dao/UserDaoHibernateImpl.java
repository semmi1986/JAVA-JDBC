package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users " +
            "(id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, name VARCHAR, lastName VARCHAR, age SMALLINT CHECK (age>=0))";
    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users";
    private static final String CLEAN_USER_SQL = "DELETE FROM users";
    private static final String SELECT_USER_SQL = "SELECT * FROM users";

    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery(CREATE_TABLE_SQL, User.class).executeUpdate();
            tx.commit();
            System.out.println("CREATE TABLE Users");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery(DROP_TABLE_SQL, User.class).executeUpdate();
            tx.commit();
            System.out.println("DROP TABLE IF EXISTS Users");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            User user = User.builder()
                    .name(name)
                    .lastName(lastName)
                    .age(age)
                    .build();
            session.save(user);
            tx.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Woy " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            tx.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            users = session.createNativeQuery(SELECT_USER_SQL, User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery(CLEAN_USER_SQL).executeUpdate();
            tx.commit();
            System.out.println("Table cleared");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}