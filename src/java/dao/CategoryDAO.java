package dao;

import config.DBConnect;
import entity.Book;
import entity.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class CategoryDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Category> getTopCategories() throws ClassNotFoundException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT TOP (5) c.category_id, c.name, COUNT(bc.book_id) AS book_count "
                + "FROM Category c "
                + "JOIN BookCategory bc ON c.category_id = bc.category_id "
                + "GROUP BY c.category_id, c.name "
                + "ORDER BY book_count DESC";

        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int categoryId = rs.getInt("category_id");
                String name = rs.getString("name");
                categories.add(new Category(categoryId, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

}
