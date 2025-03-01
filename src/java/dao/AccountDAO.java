/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DBConnect;
import entity.Account;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class AccountDAO {

    Connection conn = null;

//    /**
//     *
//     */
//    public AccountDAO() {
//        try {
//            conn = DBConnect.connect();
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    PreparedStatement ps = null;
    ResultSet rs = null;

    public Account login(String username, String password) throws ClassNotFoundException {
        String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
        try {
            conn = DBConnect.connect();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getBoolean("role") // 1: admin, 0: user
                );
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int accountId) throws ClassNotFoundException {
        String sql = "SELECT a.account_id, a.username, a.email, a.password, a.role, "
                + "FROM Account "
                + "WHERE a.account_id = ?";

        // Kết nối và thực hiện truy vấn
        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);  // Gán giá trị accountId vào truy vấn
            ResultSet rs = ps.executeQuery();

            // Kiểm tra nếu có kết quả trả về
            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getBoolean("role") // Lấy thông tin role (admin/user)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Nếu không tìm thấy người dùng
    }

    public boolean updateAccount(int accountId, String username, String password, String email) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Account SET username = ?, password = ?, email = ? WHERE accountId = ?";
        try ( Connection conn = DBConnect.connect();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setInt(4, accountId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Phương thức cập nhật mật khẩu
    public boolean changePassword(int accountId, String oldPassword, String newPassword) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmtCheck = null;
        PreparedStatement pstmtUpdate = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.connect();
            conn.setAutoCommit(false);

            // Kiểm tra mật khẩu cũ có đúng không
            String queryCheck = "SELECT password FROM Account WHERE account_id = ?";
            pstmtCheck = conn.prepareStatement(queryCheck);
            pstmtCheck.setInt(1, accountId);
            rs = pstmtCheck.executeQuery();

            if (rs.next()) {
                String currentPasswordDB = rs.getString("password");

                if (!currentPasswordDB.equals(oldPassword)) {
                    return false; // Mật khẩu cũ không đúng
                }
            } else {
                return false; // Không tìm thấy tài khoản
            }

            // Cập nhật mật khẩu mới
            String queryUpdate = "UPDATE Account SET password = ? WHERE account_id = ?";
            pstmtUpdate = conn.prepareStatement(queryUpdate);
            pstmtUpdate.setString(1, newPassword);
            pstmtUpdate.setInt(2, accountId);
            pstmtUpdate.executeUpdate();

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmtCheck != null) {
                    pstmtCheck.close();
                }
                if (pstmtUpdate != null) {
                    pstmtUpdate.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
