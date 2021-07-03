package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS User " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)").addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();

    }

    @Override
    public void dropUsersTable() {
        session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.
                createSQLQuery("DROP TABLE IF EXISTS User").addEntity(User.class);
        query.executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = Util.getSessionFactory().openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.save(new User(name,lastName,age));
            transaction.commit();
            System.out.println("User is created");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void removeUserById(long id) {
        try {
            session = Util.getSessionFactory().openSession();
            Transaction transaction = session.getTransaction();
            transaction.begin();

            //Delete a persistent object
            User user = (User) session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                System.out.println("user is deleted");
            }
            transaction.commit();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }


    }

    @Override
    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            list = session.createSQLQuery("select * from User").
                        addEntity(User.class).list();

            //list = session.createCriteria(User.class).list();
            session.getTransaction().commit();
            session.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try{    session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            List<?> instances = session.createCriteria(User.class).list();
            for (Object obj : instances) {
                session.delete(obj);
            }
            session.getTransaction().commit();
            session.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
