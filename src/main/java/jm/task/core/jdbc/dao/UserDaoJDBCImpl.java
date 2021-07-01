package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.Column;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    public void tryingDao(String sqlCommand, String Message) {
        try (Connection conn = Util.getMySQLConnection()){
            Statement statement = conn.createStatement();
            statement.executeUpdate(sqlCommand);
            System.out.println(Message);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE if not exists Users(" +
                "    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT ," +
                "    firstName CHAR(20)," +
                "    lastName CHAR(20)," +
                "    age TINYINT" +
                ")";
        tryingDao(sqlCommand,"Table is created");
    }

    public void dropUsersTable() {
        String sqlCommand = "DROP TABLE Users";
        tryingDao(sqlCommand,"Table is dropped");
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlCommand = "insert into Users(firstName,lastName,age)" +
                " values('"+name+"','"+lastName+"',"+age+")";
        tryingDao(sqlCommand,"User с именем – "+name+" добавлен в базу данных");
    }

    public void removeUserById(long id) {
        String sqlCommand = "DELETE FROM Users WHERE id ="+id;
        tryingDao(sqlCommand,"User is removed");
    }

    public List<User> getAllUsers() {
        String sqlCommand = "select * from Users";
        List<User> listUsers = new ArrayList<>();
        try (Connection conn = Util.getMySQLConnection()){
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while(resultSet.next()){
                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                Byte age = resultSet.getByte(4);
                User user = new User(name,lastName,age);
                user.setId(id);
                listUsers.add(user);
            }
         } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Users are recieved");
        return listUsers;
    }

    public void cleanUsersTable() {
        String sqlCommand = "DELETE FROM Users WHERE id >=1";
        tryingDao(sqlCommand,"Table is clean");
    }
}
