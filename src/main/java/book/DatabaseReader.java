package book;

import com.alibaba.fastjson2.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

        int pageSize = 6; // 每页显示的数据量
        int offset = (page - 1) * pageSize; // 计算偏移量

        List<Dataitem> dataList = Dataitem.fetchDataFromDatabase(jdbcUrl, username, password, searchType, filter, offset, pageSize);

        // 转换 Dataitem 对象为包含图像数据的 JSON 字符串
        List<String> jsonList = new ArrayList<>();
        for (Dataitem dataItem : dataList) {
            // 使用自定义方法将 Dataitem 转换为 JSON 字符串，包含图像数据
            String jsonString = convertDataitemToJSON(dataItem);
            jsonList.add(jsonString);
        }

        // 将 JSON 字符串列表转换为数组
        String[] jsonArr = jsonList.toArray(new String[0]);

        // 发送 JSON 数组作为响应
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write("[" + String.join(",", jsonArr) + "]");
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    // 自定义方法，将 Dataitem 转换为 JSON 字符串，包含图像数据
    private String convertDataitemToJSON(Dataitem dataItem) {
        JSONObject json = new JSONObject();
        json.put("book", dataItem.getBook());
        json.put("author", dataItem.getAuthor());
        json.put("nation", dataItem.getNation());

        // 检查图像是否为 null
        if (dataItem.getImage() != null) {
            // 创建图像 URL
            String imageUrl = "http://10.69.219.123:8080/JAVA_FINAL_WORK/uploads/" + dataItem.getImage();
            json.put("image", imageUrl);
        } else {
            // 图像为 null，可以选择设置默认值或者省略该字段
            //json.put("image", "defaultImageUrl");
            // 或者
            // 不添加 image 字段
        }

        return json.toJSONString();
    }

}
