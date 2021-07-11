package servlets.filter;

import by.entity.Roles;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;

import static java.util.Objects.nonNull;

@WebFilter("/download")
public class DownloadFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException, NullPointerException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Path path = Path.of(req.getParameter("path"));
        HttpSession session = req.getSession();
        String[] fullPath = path.toString().split("\\\\");
        String name = fullPath[fullPath.length - 1];
        if (name.contains(".txt")) {
            filterChain.doFilter(req, resp);
        } else if (nonNull(session.getAttribute("role"))) {
            if (session.getAttribute("role").equals(Roles.ADMIN)
                    || session.getAttribute("role").equals(Roles.USER)) {
                filterChain.doFilter(req, resp);
            }
        } else {
            resp.sendRedirect("http://localhost:8080/");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
