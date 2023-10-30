import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;


@WebServlet("/req1")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // 获取所有参数的Map集合
        Enumeration<String> parameterNames = req.getParameterNames();

        // 输出 HTML 页面
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Login Success</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h2>Parameters:</h2>");
        out.println("<ul>");
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = req.getParameter(paramName);
            out.println("<li>" + paramName + ": " + paramValue + "</li>");
        }
        out.println("</ul>");

        out.println("<h2>Headers:</h2>");
        out.println("<ul>");
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = req.getHeader(headerName);
            out.println("<li>" + headerName + ": " + headerValue + "</li>");
        }
        out.println("</ul>");

        out.println("</body>");
        out.println("</html>");
    }
}
