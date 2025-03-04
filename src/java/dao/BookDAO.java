/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DBConnect;
import entity.Book;
import entity.Category;
import dao.CategoryDAO;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class BookDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        BookDAO dao = new BookDAO();
        List<Book> list = dao.getAllBooks();
        List<Category> listC = dao.getAllCategory();

        for (Category o : listC) {
            System.out.println(o);
        }
    }

    // Lấy ra tất cả các sách có trong hệ thống
    public List<Book> getAllBooks() throws ClassNotFoundException, SQLException {
        List<Book> list = new ArrayList<>();
        String query = "SELECT * FROM Books";

        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Book(rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("cover_image"),
                        rs.getString("file_path")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources in a finally block
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace(); // Handle any potential exceptions while closing
            }
        }
        return list;
    }

    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        String query = "SELECT * FROM Category";
        try {
            conn = new DBConnect().connect();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt(1),
                        rs.getString(2)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Book> getBookById(int book_id) {
        List<Book> list = new ArrayList<>();
        String query = "SELECT * FROM Books\n"
                + "WHERE book_id = ?";
        try {
            conn = new DBConnect().connect();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setInt(1, book_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Book(rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("cover_image"),
                        rs.getString("file_path")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Map<String, String>> getRandomBooks() {
        List<Map<String, String>> books = new ArrayList<>();
        String query = "SELECT TOP 5 [cover_image], [description] FROM Books ORDER BY NEWID()";

        try {
            conn = new DBConnect().connect(); // Mở kết nối
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                String imageUrl = rs.getString("cover_image");
                String desc = rs.getString("description");

                // Nếu ảnh rỗng, đặt ảnh mặc định
                if (imageUrl == null || imageUrl.trim().isEmpty()) {
                    imageUrl = "./images/default-cover-book-1.jpg";
                }
                // Nếu mô tả rỗng, đặt mô tả mặc định
                if (desc == null || desc.trim().isEmpty()) {
                    desc = "No description available.";
                }

                // Lưu thông tin vào Map
                Map<String, String> book = new HashMap<>();
                book.put("cover_image", imageUrl);
                book.put("description", desc);
                books.add(book);
            }

            // Nếu số lượng sách lấy ra không đủ 5, thêm sách mặc định
            while (books.size() < 5) {
                Map<String, String> defaultBook = new HashMap<>();
                defaultBook.put("cover_image", "./images/default-cover-book-1.jpg");
                defaultBook.put("description", "No description available.");
                books.add(defaultBook);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi
            // Nếu có lỗi, thêm 5 sách mặc định
            books.clear();
            for (int i = 0; i < 5; i++) {
                Map<String, String> defaultBook = new HashMap<>();
                defaultBook.put("cover_image", "./images/default-cover-book-1.jpg");
                defaultBook.put("description", "No description available.");
                books.add(defaultBook);
            }
        } finally {
            // Đóng kết nối
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return books;
    }

    public List<Book> getSort() {
        String query = "SELECT * FROM Books\n"
                + "ORDER BY book_id ASC";
        List<Book> list = new ArrayList<>();
        try {
            conn = new DBConnect().connect();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Book(rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("cover_image"),
                        rs.getString("file_path")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Book> getTop4() {
        List<Book> list = new ArrayList<>();
        String query = "SELECT TOP 4 * FROM Books ORDER BY NEWID()";
        try {
            conn = new DBConnect().connect(); // Mở kết nối
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getString(6),
                        rs.getString(7)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // In ra console để kiểm tra
        System.out.println("Số lượng sách lấy được: " + list.size());
        for (Book book : list) {
            System.out.println(book);
        }

        return list;
    }

    public List<Book> getTopBooksByCategory(int categoryId) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT TOP (8) b.book_id, b.title, b.author, b.description, b.price, b.cover_image, b.file_path "
                + "FROM Books b "
                + "JOIN BookCategory bc ON b.book_id = bc.book_id "
                + "WHERE bc.category_id = ?";

        try ( Connection conn = new DBConnect().connect();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                            rs.getInt("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getString("cover_image"),
                            rs.getString("file_path")));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> getTop8Books() throws ClassNotFoundException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT TOP 8 * FROM books ORDER BY NEWID();"; // Chọn ngẫu nhiên 8 quyển sách
        try ( Connection conn = DBConnect.connect();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                books.add(new Book(rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("cover_image"),
                        rs.getString("file_path")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

}
