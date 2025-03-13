package dao;

import config.DBConnect;
import entity.Book;
import entity.Category;
import entity.Interaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InteractionDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    //user add comment
    public boolean addComment(int account_id, int book_id, double rating, String comment) {
        String query = "INSERT INTO Interaction(account_id, book_id, rating, comment) VALUES(?, ?, ?, ?)";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, account_id);
            ps.setInt(2, book_id);
            ps.setDouble(3, rating);
            ps.setString(4, comment);
            return ps.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Get comments for a specific book
    public List<Interaction> getCommentsByBookId(int bookId) {
        List<Interaction> commentList = new ArrayList<>();
        String query = "SELECT i.interaction_id, i.account_id, i.book_id, i.rating, i.comment, i.created_at, " +
                      "a.username " +
                      "FROM Interaction i " +
                      "JOIN Account a ON i.account_id = a.account_id " +
                      "WHERE i.book_id = ? " +
                      "ORDER BY i.created_at DESC";
        
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Interaction interaction = new Interaction(
                    rs.getInt("interaction_id"),
                    rs.getInt("account_id"),
                    rs.getInt("book_id"),
                    null, // action is null for comments
                    rs.getDouble("rating"),
                    rs.getString("comment"),
                    rs.getString("username"),
                    rs.getTimestamp("created_at")
                );
                commentList.add(interaction);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return commentList;
    }
    
    // Get average rating for a book
    public double getAverageRatingByBookId(int bookId) {
        String query = "SELECT AVG(rating) as avg_rating FROM Interaction WHERE book_id = ?";
        double avgRating = 0.0;
        
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                avgRating = rs.getDouble("avg_rating");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return avgRating;
    }
    
    // Count total reviews for a book
    public int getReviewCountByBookId(int bookId) {
        String query = "SELECT COUNT(*) as review_count FROM Interaction WHERE book_id = ?";
        int reviewCount = 0;
        
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                reviewCount = rs.getInt("review_count");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return reviewCount;
    }
    
    public boolean reportComment(int interactionId, int reporterId) {
        String query = "UPDATE Interaction SET status = 1 WHERE interaction_id = ? AND account_id = ?";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, interactionId);
            ps.setInt(2, reporterId);
            return ps.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}