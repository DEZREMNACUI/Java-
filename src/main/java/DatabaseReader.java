import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet( urlPatterns = "/DatabaseReader")
public class DatabaseReader extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*"); // 允许任何来源访问
        resp.setHeader("Access-Control-Allow-Methods", "GET"); // 允许 GET 请求
        resp.setHeader("Access-Control-Allow-Methods", "POST");
        resp.setHeader("Content-Type", "application/json");
        String jdbcUrl = "jdbc:mysql://10.69.219.123:3306/db2";
        String username = "1";
        String password = "13550581080";

        List<Dataitem> dataList = Dataitem.fetchDataFromDatabase(jdbcUrl, username, password);

        String jsonString = JSON.toJSONString(dataList);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(jsonString);
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
