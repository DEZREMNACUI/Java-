package book;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(urlPatterns = "/Delete")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private void deleteDataFromDatabase(String jdbcUrl, String username, String password,
                                        String book, String author, String nation) {
        // 调用 book.Dataitem 类中的删除方法
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


        String s1;
        try {
            s1 = Dataitem.returnimg(jdbcUrl, username, password, book, author, nation);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String fileUrl = "E:/IntelliJ IDEA 2023.2.2/projects/JAVA_FINAL_WORK/src/main/webapp/uploads/" + s1; // 替换为实际文件的URL



        String filePath = fileUrl; // 替换为实际文件的本地路径

        File fileToDelete = new File(filePath);

        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete file.");
            }
        } else {
            System.out.println("File not found.");
        }



        // 执行删除操作
        deleteDataFromDatabase(jdbcUrl, username, password, book, author, nation);



    }
}
