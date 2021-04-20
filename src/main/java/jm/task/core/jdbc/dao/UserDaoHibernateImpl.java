package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession().openSession()){
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastName` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT(3) NOT NULL)")
                    .addEntity(User.class)
                    .executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS  users")
                    .addEntity(User.class)
                    .executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем - " + name +" добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(session.load(User.class, id));
            transaction.commit();
            System.out.println("User удален");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();
        try (Session session = Util.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            list = session.createQuery("FROM User").list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            List<User> users = session.createSQLQuery("SELECT * FROM users").addEntity(User.class).list();
            for (User user : users) {
                session.delete(user);
            }
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
