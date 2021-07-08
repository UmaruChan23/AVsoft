package servlets.controller;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/download")
public class DownloadController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path path = Path.of(req.getParameter("path"));
        byte[] byteArray = Files.readAllBytes(path);
        String[] name = path.toString().split("\\\\");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + name[name.length - 1] + "\"");
        resp.setContentType("application/octet-stream");
        req.setAttribute("file", byteArray);
        ServletOutputStream out = resp.getOutputStream();
        out.write(byteArray);
        out.flush();
        out.close();
    }
}
