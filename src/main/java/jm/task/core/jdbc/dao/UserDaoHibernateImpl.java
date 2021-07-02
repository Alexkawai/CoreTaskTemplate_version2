package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        /*Transaction transaction = Util.getSession().beginTransaction();
        User user = new User(name,lastName,age);
        try{
            Util.getSession().save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        transaction.commit();
        String hql = "insert into user (firstName,lastName,age) " +
                "select '"+name+"','"+lastName+"',"+age+" from user";
        int rows = session.createQuery (hql).executeUpdate();*/

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

        List<User> list = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            list = session.createQuery( "FROM User").list();

            session.getTransaction().commit();
            session.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {

    }
}
