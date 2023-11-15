import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet(urlPatterns = "/Add")
public class Addservlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jdbcUrl = "jdbc:mysql://10.69.219.123:3306/db2";
        String username = "1";
        String password = "13550581080";

        // 从请求中获取 JSON 数据
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        // 解析 JSON 数据
        JSONObject json = JSONObject.parseObject(sb.toString());

        // 从 JSON 中获取需要添加的书籍信息
        String book = json.getJSONObject("data").getString("book");
        String author = json.getJSONObject("data").getString("author");
        String nation = json.getJSONObject("data").getString("nation");

        // 调用 Dataitem 类中的添加方法
        Dataitem.addDataToDatabase(jdbcUrl, username, password, book, author, nation);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
