package login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/req2")
public class ZhucheServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String tel = req.getParameter("tel");

        Cookie username1 = new Cookie("username", username);
        Cookie password1 = new Cookie("password", password);
        Cookie tel1 = new Cookie("tel", tel);

        username1.setMaxAge((24*60*60));
        password1.setMaxAge((24*60*60));
        tel1.setMaxAge((24*60*60));

        resp.addCookie(username1);
        resp.addCookie(password1);
        resp.addCookie(tel1);
        resp.sendRedirect("index.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
