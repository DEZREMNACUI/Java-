import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/DatabaseReader")
public class DatabaseReader extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET");
        resp.setHeader("Content-Type", "application/json");

        String jdbcUrl = "jdbc:mysql://10.69.219.123:3306/db2";
        String username = "1";
        String password = "13550581080";

        String searchType = req.getParameter("searchType");
        String filter = req.getParameter("filter");
        int page = Integer.parseInt(req.getParameter("page")); // 获取传递的页码参数

        int pageSize = 3; // 每页显示的数据量
        int offset = (page - 1) * pageSize; // 计算偏移量

        List<Dataitem> dataList = Dataitem.fetchDataFromDatabase(jdbcUrl, username, password, searchType, filter, offset, pageSize);

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
