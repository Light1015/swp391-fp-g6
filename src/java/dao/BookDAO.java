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
import java.util.Optional;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class BookDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

//
//    private Connection conn;
//
//    public BookDAO(Connection conn) {
//        this.conn = conn;
//    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        BookDAO dao = new BookDAO();
        List<Book> list = dao.getAllBooks();
        List<Category> listC = dao.getAllCategory();

        for (Category o : listC) {
            System.out.println(o);
        }
    }

    // Lấy danh sách tất cả sách kèm tên tác giả và thể loại
    public List<Book> getAllBooks() throws ClassNotFoundException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, a.name AS authorName, s.name AS seriesName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + "JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b "
                + "JOIN Author a ON b.author_id = a.author_id "
                + "LEFT JOIN BookSeries s ON b.series_id = s.series_id";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("book_id"), rs.getString("title"), rs.getInt("author_id"), rs.getString("description"),
                        rs.getDouble("price"), rs.getString("cover_image"), rs.getString("file_path"), rs.getString("publisher"),
                        rs.getInt("publication_year"), rs.getInt("stock_quantity"), rs.getString("language"),
                        rs.getInt("series_id"), rs.getInt("volume_number"), rs.getString("book_type"), rs.getInt("created_by")
                );
                book.setAuthorName(rs.getString("authorName"));
                book.setSeriesName(rs.getString("seriesName"));
                book.setCategories(rs.getString("categories"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return books;
    }

    // Lấy sách theo ID
    public Book getBookById(int bookId) throws ClassNotFoundException {
        String sql = "SELECT b.*, a.name AS authorName, s.name AS seriesName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + "JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b "
                + "JOIN Author a ON b.author_id = a.author_id "
                + "LEFT JOIN BookSeries s ON b.series_id = s.series_id "
                + "WHERE b.book_id = ?";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Book book = new Book(
                        rs.getInt("book_id"), rs.getString("title"), rs.getInt("author_id"), rs.getString("description"),
                        rs.getDouble("price"), rs.getString("cover_image"), rs.getString("file_path"), rs.getString("publisher"),
                        rs.getInt("publication_year"), rs.getInt("stock_quantity"), rs.getString("language"),
                        rs.getInt("series_id"), rs.getInt("volume_number"), rs.getString("book_type"), rs.getInt("created_by")
                );
                book.setAuthorName(rs.getString("authorName"));
                book.setSeriesName(rs.getString("seriesName"));
                book.setCategories(rs.getString("categories"));
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    // Lấy danh sách tất cả danh mục
    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        String query = "SELECT * FROM Category";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt("category_id"), rs.getString("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return list;
    }

    // Lấy sách theo danh mục
    public List<Book> getBooksByCategory(int categoryId) throws ClassNotFoundException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, a.name AS authorName, s.name AS seriesName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + "JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b "
                + "JOIN Author a ON b.author_id = a.author_id "
                + "LEFT JOIN BookSeries s ON b.series_id = s.series_id "
                + "JOIN BookCategory bc ON b.book_id = bc.book_id "
                + "WHERE bc.category_id = ?";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("book_id"), rs.getString("title"), rs.getInt("author_id"), rs.getString("description"),
                        rs.getDouble("price"), rs.getString("cover_image"), rs.getString("file_path"), rs.getString("publisher"),
                        rs.getInt("publication_year"), rs.getInt("stock_quantity"), rs.getString("language"),
                        rs.getInt("series_id"), rs.getInt("volume_number"), rs.getString("book_type"), rs.getInt("created_by")
                );
                book.setAuthorName(rs.getString("authorName"));
                book.setSeriesName(rs.getString("seriesName"));
                book.setCategories(rs.getString("categories"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return books;
    }

    // Lấy 5 sách ngẫu nhiên (chỉ lấy ảnh bìa và mô tả)
    public List<Map<String, String>> getRandomBooks() {
        List<Map<String, String>> books = new ArrayList<>();
        String query = "SELECT TOP 5 cover_image, description FROM Books ORDER BY NEWID()";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> book = new HashMap<>();
                book.put("cover_image", Optional.ofNullable(rs.getString("cover_image")).orElse("./images/default-cover-book-1.jpg"));
                book.put("description", Optional.ofNullable(rs.getString("description")).orElse("No description available."));
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return books;
    }

    // Lấy danh sách sách sắp xếp theo ID, kèm tên tác giả và danh mục
    public List<Book> getSort() {
        List<Book> list = new ArrayList<>();
        String query = "SELECT b.*, a.name AS authorName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + " JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b JOIN Author a ON b.author_id = a.author_id ORDER BY b.book_id ASC";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                list.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return list;
    }

    // Lấy 4 sách ngẫu nhiên, kèm tên tác giả và danh mục
    public List<Book> getTop4() {
        List<Book> list = new ArrayList<>();
        String query = "SELECT TOP 4 b.*, a.name AS authorName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + " JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b JOIN Author a ON b.author_id = a.author_id ORDER BY NEWID()";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                list.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return list;
    }

    // Lấy top 8 sách theo danh mục, kèm tên tác giả và danh mục
    public List<Book> getTopBooksByCategory(int categoryId) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT TOP 8 b.*, a.name AS authorName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + " JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b JOIN Author a ON b.author_id = a.author_id "
                + "JOIN BookCategory bc ON b.book_id = bc.book_id WHERE bc.category_id = ?";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return books;
    }

    // Lấy 8 sách ngẫu nhiên, kèm tên tác giả và danh mục
    public List<Book> getTop8Books() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT TOP 8 b.*, a.name AS authorName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + " JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b JOIN Author a ON b.author_id = a.author_id ORDER BY NEWID()";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return books;
    }

    // Phương thức để trích xuất dữ liệu từ ResultSet và tạo đối tượng Book
    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book(
                rs.getInt("book_id"),
                rs.getString("title"),
                rs.getInt("author_id"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getString("cover_image"),
                rs.getString("file_path"),
                rs.getString("publisher"),
                rs.getInt("publication_year"),
                rs.getInt("stock_quantity"),
                rs.getString("language"),
                rs.getInt("series_id"),
                rs.getInt("volume_number"),
                rs.getString("book_type"),
                rs.getInt("created_by")
        );
        book.setAuthorName(rs.getString("authorName"));
        book.setCategories(rs.getString("categories"));
        return book;
    }

    // Phương thức này sẽ tìm các sách có cùng thể loại hoặc cùng tác giả với sách hiện tại.
    public List<Book> getSimilarBooks(int bookId) throws ClassNotFoundException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT TOP 4 b.*, a.name AS authorName, "
                + "   (SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + "    JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b "
                + "JOIN Author a ON b.author_id = a.author_id "
                + "WHERE b.book_id <> ? "
                + "AND (b.author_id = (SELECT author_id FROM Books WHERE book_id = ?) "
                + "     OR b.book_id IN (SELECT book_id FROM BookCategory WHERE category_id IN "
                + "        (SELECT category_id FROM BookCategory WHERE book_id = ?))) "
                + "ORDER BY NEWID()";

        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bookId);
            ps.setInt(2, bookId);
            ps.setInt(3, bookId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return books;
    }

    // Get categories for a specific book
    public String getCategoriesForBook(int bookId) throws ClassNotFoundException {
        String categories = "";
        String sql = "SELECT STRING_AGG(c.name, ', ') AS categories "
                + "FROM BookCategory bc "
                + "JOIN Category c ON bc.category_id = c.category_id "
                + "WHERE bc.book_id = ?";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();
            if (rs.next()) {
                categories = rs.getString("categories");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return categories;
    }

    // Đóng tài nguyên
    private void closeResources() {
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
}
