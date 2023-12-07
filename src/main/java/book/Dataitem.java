package book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Dataitem {
    private final String book;
    private final String author;
    private final String nation;
    private final String image;
    private final String content; // 新增内容属性
    private final String price;   // 新增价格属性

    public Dataitem(String book, String author, String nation, String image, String content, String price) {
        this.book = book;
        this.author = author;
        this.nation = nation;
        this.image = image;
        this.content = content; // 初始化内容
        this.price = price;     // 初始化价格
    }

    public String getBook() {
        return book;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getPrice() {
        return price;
    }

    public String getNation() {
        return nation;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "book.Dataitem{" +
                "book='" + book + '\'' +
                ", author='" + author + '\'' +
                ", nation='" + nation + '\'' +
                '}';
    }

    public static List<Dataitem> fetchDataFromDatabase(String jdbcUrl, String username, String password,
                                                       String searchType, String filter, int offset, int pageSize) {
        List<Dataitem> dataList = new ArrayList<>();

        try {
            // 加载数据库驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
                // 构建查询语句，包含图像数据的列
                String sql = "";
                switch (searchType) {
                    case "book":
                        sql = "SELECT 书名, 内容, 价格, 作者, 国籍, image FROM bookstore WHERE 书名 LIKE ? LIMIT ?, ?";
                        break;
                    case "author":
                        sql = "SELECT 书名, 内容, 价格, 作者, 国籍, image FROM bookstore WHERE 作者 LIKE ? LIMIT ?, ?";
                        break;

                }

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // 设置参数
                    preparedStatement.setString(1, "%" + filter + "%");
                    preparedStatement.setInt(2, offset);
                    preparedStatement.setInt(3, pageSize);

                    // 执行查询
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        // 处理查询结果
                        while (resultSet.next()) {
                            String book = resultSet.getString("书名");
                            String content = resultSet.getString("内容"); // 获取内容
                            String price = resultSet.getString("价格");   // 获取价格
                            String author = resultSet.getString("作者");
                            String nation = resultSet.getString("国籍");
                            String imagePath = resultSet.getString("image");

                            Dataitem dataItem = new Dataitem(book, author, nation, imagePath, content, price);
                            dataList.add(dataItem);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }


    public static void updateDataInDatabase(String jdbcUrl, String username, String password,
                                            String originalBook, String originalAuthor, String originalNation,
                                            String newBook, String newContent, String newPrice, String newAuthor, String newNation, String image) {
        try {
            // 加载数据库驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
                // 执行更新
                String sql = "UPDATE bookstore SET 书名=?, 内容=?, 价格=?, 作者=?, 国籍=?, image=? WHERE 书名=? AND 作者=? AND 国籍=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // 设置参数
                    preparedStatement.setString(1, newBook);
                    preparedStatement.setString(2, newContent); // 设置新内容
                    preparedStatement.setString(3, newPrice);   // 设置新价格
                    preparedStatement.setString(4, newAuthor);
                    preparedStatement.setString(5, newNation);
                    preparedStatement.setString(6, image);
                    preparedStatement.setString(7, originalBook);
                    preparedStatement.setString(8, originalAuthor);
                    preparedStatement.setString(9, originalNation);

                    // 执行更新操作
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("数据更新成功！");
                    } else {
                        System.out.println("未找到匹配的数据，更新失败。");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDataFromDatabase(String jdbcUrl, String username, String password,
                                              String book, String author, String nation) {
        try {
            // 加载数据库驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
                // 执行删除
                String sql = "DELETE FROM bookstore WHERE 书名=? AND 作者=? AND 国籍=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // 设置参数
                    preparedStatement.setString(1, book);
                    preparedStatement.setString(2, author);
                    preparedStatement.setString(3, nation);

                    // 执行删除操作
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("数据删除成功！");
                    } else {
                        System.out.println("未找到匹配的数据，删除失败。");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addDataToDatabase(String jdbcUrl, String username, String password,
                                         String book, String content, String price, String author, String nation, String imagePath) {
        try {
            // 加载数据库驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
                // 执行插入
                String sql = "INSERT INTO bookstore (书名, 内容, 价格, 作者, 国籍, image) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // 设置参数
                    preparedStatement.setString(1, book);
                    preparedStatement.setString(2, content); // 设置内容
                    preparedStatement.setString(3, price);   // 设置价格
                    preparedStatement.setString(4, author);
                    preparedStatement.setString(5, nation);
                    preparedStatement.setString(6, imagePath);

                    // 执行插入操作
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("数据插入成功！");
                    } else {
                        System.out.println("插入数据失败。");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public static String returnimg(String jdbcUrl, String username, String password,
                                   String book, String author, String nation) throws ClassNotFoundException {
        try {
            // 加载数据库驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
                // 执行更新
                String sql = "SELECT image FROM bookstore WHERE 书名=? AND 作者=? AND 国籍=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // 设置参数
                    preparedStatement.setString(1, book);
                    preparedStatement.setString(2, author);
                    preparedStatement.setString(3, nation);


                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        // 处理查询结果
                        while (resultSet.next()) {

                            // 获取图像文件路径
                            String imagePath = resultSet.getString("image");
                            return imagePath;

                        }


                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {

        }
        return null;
    }

//    public static List<book.Dataitem> fetchDataWithSearch(String jdbcUrl, String username, String password, String searchType, String filter) {
//        List<book.Dataitem> dataList = new ArrayList<>();
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
//                String sql = "";
//
//                switch (searchType) {
//                    case "book":
//                        sql = "SELECT 书名, 作者, 国籍 FROM bookstore WHERE 书名 LIKE ?";
//                        break;
//                    case "author":
//                        sql = "SELECT 书名, 作者, 国籍 FROM bookstore WHERE 作者 LIKE ?";
//                        break;
//                    // 添加其他搜索类型的逻辑
//                }
//
//                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                    preparedStatement.setString(1, "%" + filter + "%");
//
//                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                        while (resultSet.next()) {
//                            String book = resultSet.getString("书名");
//                            String author = resultSet.getString("作者");
//                            String nation = resultSet.getString("国籍");
//
//                            book.Dataitem dataItem = new book.Dataitem(book, author, nation);
//                            dataList.add(dataItem);
//                        }
//                    }
//                }
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//
//        return dataList;
//    }


}
