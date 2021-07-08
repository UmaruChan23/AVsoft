package servlets;

import by.dao.UserData;
import by.entity.Roles;
import by.entity.UserEntity;
import by.utils.HibernateUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    private UserData repo = new UserData();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        HibernateUtils.CreateSessionFactory();
        UserEntity admin = new UserEntity();
        admin.setUsername("Admin");
        admin.setPassword("JA3uxTSPVbEN5KJ8");
        admin.setRole(Roles.ADMIN);
        if(!repo.userIsExist(admin.getUsername(), admin.getPassword())) repo.add(admin);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        repo = null;
    }
}
