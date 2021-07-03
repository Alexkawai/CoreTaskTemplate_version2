package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Util {
    public static Connection getMySQLConnection() throws SQLException,
            ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "newdb";
        String userName = "root";
        String password = "123456";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException,
            ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?serverTimezone=UTC&useSSL=false";

        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }


    /*public static Session getSession() throws HibernateException {
        Map<String, String> settings = new HashMap<>();
        settings.put("connection.driver_class", "com.mysql.cj.jdbc.Driver");
        //settings.put("dialect", "org.hibernate.dialect.MySQL8Dialect");
        settings.put("hibernate.connection.url",
                "jdbc:mysql://localhost:3306/newdb?serverTimezone=UTC&useSSL=false");
        settings.put("hibernate.connection.username", "root");
        settings.put("hibernate.connection.password", "123456");
        settings.put("hibernate.current_session_context_class", "thread");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.hbm2ddl.auto", "update");
        Session session = null;
        try {

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(settings).build();

            MetadataSources metadataSources = new MetadataSources(serviceRegistry).
                    addAnnotatedClass(User.class);

            Metadata metadata = metadataSources.buildMetadata();

            SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
            session = sessionFactory.getCurrentSession();
            System.out.println("Session is done");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return session;
    }*/
    private static StandardServiceRegistry registry;
    private static SessionFactory  sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder registryBuilder =
                        new StandardServiceRegistryBuilder();

                Map<String, String> settings = new HashMap<>();
                settings.put("connection.driver_class", "com.mysql.cj.jdbc.Driver");
                settings.put("dialect", "org.hibernate.dialect.MySQL8Dialect");
                settings.put("hibernate.connection.url",
                        "jdbc:mysql://localhost:3306/newdb?serverTimezone=UTC&useSSL=false");
                settings.put("hibernate.connection.username", "root");
                settings.put("hibernate.connection.password", "123456");
                settings.put("hibernate.current_session_context_class", "thread");
                settings.put("hibernate.show_sql", "true");
                settings.put("hibernate.format_sql", "true");
                settings.put("hibernate.hbm2ddl.auto", "update");
                settings.put("hibernate.bytecode.use_reflection_optimizer", "false");


                registryBuilder.applySettings(settings);
                registry = registryBuilder.build();
                MetadataSources sources = new MetadataSources(registry).
                            addAnnotatedClass(User.class);

                sessionFactory = sources.buildMetadata().buildSessionFactory();

            } catch (Exception e) {
                System.out.println("SessionFactory creation failed");
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;

    }




}
