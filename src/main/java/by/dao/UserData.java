package by.dao;

import by.entity.Roles;
import by.entity.UserEntity;
import by.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class UserData {

    public UserEntity getUserByLoginPassword(final String login, final String password) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        if (login == null || password == null) {
            return null;
        }
        Session session = sessionFactory.openSession();

        Query query = session.createQuery("FROM UserEntity u where u.username= :login AND u.password= :password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        UserEntity res = null;
        if (!query.getResultList().isEmpty()) {
            res = (UserEntity) query.getSingleResult();
        }
        session.close();

        return res;
    }

    public UserEntity getUserByName(String name) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        if (name == null) {
            return null;
        }
        Session session = sessionFactory.openSession();

        Query query = session.createQuery("FROM UserEntity u where u.username= :name");
        query.setParameter("name", name);
        UserEntity res = null;
        if (!query.getResultList().isEmpty()) {
            res = (UserEntity) query.getSingleResult();
        }
        session.close();

        return res;
    }

    public List<UserEntity> getAllUsers() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<UserEntity> users = session.createQuery("FROM UserEntity u").getResultList();
        return users;
    }

    public boolean add(final UserEntity user) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        if(userIsExist(user.getUsername(), user.getPassword())){
            return false;
        }
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateUserInfo(final UserEntity user) {
        if(!userIsExist(user)){
            return false;
        }
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Roles getRoleByLoginPassword(final String login, final String password) {
        Roles result = Roles.UNKNOWN;

        UserEntity user = getUserByLoginPassword(login, password);
        if (user != null) {
            result = user.getRole();
        }

        return result;
    }

    public boolean userIsExist(final String login, final String password) {
        return getUserByLoginPassword(login, password) != null;
    }

    public boolean userIsExist(UserEntity user) {
        return getUserByLoginPassword(user.getUsername(), user.getPassword()) != null;
    }

    public boolean changeUserRole(String targetUser, String role, String reqRole) {
        if (reqRole.equalsIgnoreCase("ADMIN")) {
            Roles newRole = Roles.UNKNOWN;
            if(role.equalsIgnoreCase("ADMIN")){
                newRole = Roles.ADMIN;
            } else if(role.equalsIgnoreCase("USER")) {
                newRole = Roles.USER;
            }
            UserEntity user = getUserByName(targetUser);
            user.setRole(newRole);
            return updateUserInfo(user);
        }
        return false;
    }

    public boolean deleteUser(UserEntity user){
        if(!userIsExist(user)){
            return false;
        }
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}