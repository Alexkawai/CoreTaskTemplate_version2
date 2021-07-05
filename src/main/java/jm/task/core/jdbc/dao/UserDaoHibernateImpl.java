package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSessionFactory();
    private Session session;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        session = sessionFactory.openSession();
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

        session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.
                createSQLQuery("DROP TABLE IF EXISTS User").addEntity(User.class);
        query.executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(new User(name,lastName,age));
        transaction.commit();
        session.close();
        System.out.println("User is created");

    }

    @Override
    public void removeUserById(long id) {

        session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        User user = (User) session.get(User.class, id);
        if (user != null) {
            session.delete(user);
            System.out.println("user is deleted");
        }
        transaction.commit();
        session.close();

    }

    @Override
    public List<User> getAllUsers() {

        session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> list = session.createSQLQuery("select * from User").
                    addEntity(User.class).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public void cleanUsersTable() {

        session = sessionFactory.openSession();
        session.beginTransaction();
        List<?> instances = session.createCriteria(User.class).list();
        for (Object obj : instances) {
            session.delete(obj);
        }
        session.getTransaction().commit();
        session.close();

    }
}
