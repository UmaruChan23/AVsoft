package by.dao;

import by.entity.Roles;
import by.entity.UserEntity;
import by.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;

public class UserData {

    public UserEntity getUserByLoginPassword(final String login, final String password) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        if(login == null || password == null){
            return null;
        }
        Session session = sessionFactory.openSession();

        Query query = session.createQuery("FROM UserEntity u where u.username= :login AND u.password= :password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        UserEntity res = null;
        if(!query.getResultList().isEmpty()) {
            res = (UserEntity) query.getSingleResult();
        }
        session.close();

        return res;
    }

    public boolean add(final UserEntity user) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

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

    public Roles getRoleByLoginPassword(final String login, final String password) {
        Roles result = Roles.UNKNOWN;

        UserEntity user = getUserByLoginPassword(login, password);
        if(user != null){
            result = user.getRole();
        }

        return result;
    }

    public boolean userIsExist(final String login, final String password) {
        return getUserByLoginPassword(login, password) != null;
    }
}