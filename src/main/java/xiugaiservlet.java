import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(urlPatterns = "/xiugai")
public class xiugaiservlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求的输入流并读取 JSON 数据
        BufferedReader reader = req.getReader();
        StringBuilder jsonInput = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonInput.append(line);
        }

        // 使用 FastJSON 解析 JSON 数据
        JSONObject jsonObject = JSON.parseObject(jsonInput.toString());

        // 获取新的书籍信息
        String originalBook = jsonObject.getString("originalBook");
        String originalAuthor = jsonObject.getString("originalAuthor");
        String originalNation = jsonObject.getString("originalNation");

        String newBook = jsonObject.getString("newBook");
        String newAuthor = jsonObject.getString("newAuthor");
        String newNation = jsonObject.getString("newNation");

        // 调用数据库更新方法
        String jdbcUrl = "jdbc:mysql://10.69.219.123:3306/db2";
        String username = "1";
        String password = "13550581080";
        Dataitem.updateDataInDatabase(jdbcUrl, username, password,
                originalBook, originalAuthor, originalNation,
                newBook, newAuthor, newNation);

    }
}
