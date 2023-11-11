import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dataitem {
    private final String book;
    private final String author;
    private final String nation;

    public Dataitem(String book, String author, String nation) {
        this.book = book;
        this.author = author;
        this.nation = nation;
    }

    public String getBook() {
        return book;
    }

    public String getAuthor() {
        return author;
    }

    public String getNation() {
        return nation;
    }

    @Override
    public String toString() {
        return "Dataitem{" +
                "book='" + book + '\'' +
                ", author='" + author + '\'' +
                ", nation='" + nation + '\'' +
                '}';
    }

    public static List<Dataitem> fetchDataFromDatabase(String jdbcUrl, String username, String password) {
        List<Dataitem> dataList = new ArrayList<>();

        try {
            // 加载数据库驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
                // 执行查询
                String sql = "SELECT 书名, 作者, 国籍 FROM bookstore";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    // 处理查询结果
                    while (resultSet.next()) {
                        String book = resultSet.getString("书名");
                        String author = resultSet.getString("作者");
                        String nation = resultSet.getString("国籍");

                        // 创建数据项对象并添加到列表中
                        Dataitem dataItem = new Dataitem(book, author, nation);
                        dataList.add(dataItem);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
