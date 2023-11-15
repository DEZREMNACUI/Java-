import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet(urlPatterns = "/Delete")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private void deleteDataFromDatabase(String jdbcUrl, String username, String password,
                                        String book, String author, String nation) {
        // 调用 Dataitem 类中的删除方法
        Dataitem.deleteDataFromDatabase(jdbcUrl, username, password, book, author, nation);
    }

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

        // 输出接收到的 JSON 数据，方便调试
        System.out.println("Received JSON data: " + sb.toString());

        // 解析 JSON 数据
        JSONObject json = JSONObject.parseObject(sb.toString());
        JSONObject jsonData = json.getJSONObject("data");

        // 从 JSON 中获取需要删除的书籍信息
        String book = jsonData.getString("book");
        String author = jsonData.getString("author");
        String nation = jsonData.getString("nation");

        // 输出解析后的书籍信息，方便调试
        System.out.println("Parsed book: " + book + ", author: " + author + ", nation: " + nation);

        // 执行删除操作
        deleteDataFromDatabase(jdbcUrl, username, password, book, author, nation);
    }
}
