package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("""
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
                        owner to postgres;""");
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS users;");
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            final PreparedStatement statement =
                    connection.prepareStatement("insert into users (name, last_name, age) values (?,?,?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            final PreparedStatement statement =
                    connection.prepareStatement("delete from users where id = ?");
            statement.setLong(1, id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try {
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery("SELECT * FROM users");
            final List<User> users = new ArrayList<>();
            while (rs.next()) {
                final User user = new User(rs.getString(2), rs.getString(3), rs.getByte(4));
                user.setId(rs.getLong(1));
                users.add(user);
            }
            statement.close();
            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try {
            final Statement statement = connection.createStatement();
            statement.execute("TRUNCATE TABLE users");
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
