package servlets.controller;

import by.dao.UserData;
import by.entity.UserEntity;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/roles")
public class RolesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<String> userList = new ArrayList<>();
        for(UserEntity user : new UserData().getAllUsers()) {
            userList.add(user.getUsername() + " " + user.getRole().toString());
        }
        req.setAttribute("userList", userList);
        req.getRequestDispatcher("/WEB-INF/view/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();

        String name = req.getParameter("name");
        String role = req.getParameter("role");
        UserData repo = new UserData();
        repo.changeUserRole(name, role, session.getAttribute("role").toString());
        resp.setHeader("Refresh", "1");
    }
}
