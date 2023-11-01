import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet("/req1")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Cookie[] cookies = req.getCookies();
        boolean flag;
        boolean flag1 = false, flag2 = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                flag = cookie.getName().equals("username") && cookie.getValue().equals(username);
                if (flag) {
                    for (Cookie cookie1 : cookies) {
                        flag = cookie1.getName().equals("password") && cookie1.getValue().equals(password);
                        if (flag) {
                            flag1 = true;
                            break;
                        }
                    }
                }
                if (flag1) {
                    break;
                }
            }

            String userInput = req.getParameter("captchaInput");
            String storedCaptcha = (String) req.getSession().getAttribute("captcha");
            if (userInput != null && userInput.equalsIgnoreCase(storedCaptcha)) {
                flag2 = true;
            }
            if(flag1&&flag2) {
                HttpSession session = req.getSession();
                session.setAttribute("loginStatus", "loginIn");

                resp.sendRedirect("LoginSuccess.html");
            } else {
                resp.setContentType("text/html");
                PrintWriter out = resp.getWriter();
                out.println("<html><body>");
                out.println("<h2 style ='text-align: center;'>账户或密码或验证码不正确,5秒后返回登录界面</h2>");
                out.println("</body><html>");

                resp.setHeader("Refresh","5; URL=index.html" );
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
