package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";


    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection OK");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }
    private static SessionFactory sessionFactory;
    public static SessionFactory getSession() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.addAnnotatedClass(User.class);
                configuration.setProperty(Environment.DRIVER, DB_DRIVER);
                configuration.setProperty(Environment.URL, DB_URL);
                configuration.setProperty(Environment.USER, DB_USERNAME);
                configuration.setProperty(Environment.PASS, DB_PASSWORD);

                sessionFactory = configuration.buildSessionFactory();
                System.out.println("Сессия установлена");
            } catch (Exception e) {
                System.out.println("Сессия не установлена");
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }
}
