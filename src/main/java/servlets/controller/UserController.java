package servlets.controller;

import by.dao.UserData;
import by.entity.Roles;
import by.entity.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;

@WebServlet("/users")
public class UserController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserData repo = new UserData();
        UserEntity user = takeUserFromRequest(req);
        if(repo.add(user)) {
            req.getSession().setAttribute("password", user.getPassword());
            req.getSession().setAttribute("login", user.getUsername());
            req.getSession().setAttribute("role", user.getRole());
            req.getRequestDispatcher("/WEB-INF/view/user_menu.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("http://localhost:8080/");
        }
    }

    protected UserEntity takeUserFromRequest(HttpServletRequest req) {
        UserEntity user = new UserEntity();
        user.setUsername(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        if(nonNull(req.getParameter("role"))) {
            user.setRole(req.getParameter("role"));
        } else {
            user.setRole(Roles.USER);
        }
        return user;
    }

}
