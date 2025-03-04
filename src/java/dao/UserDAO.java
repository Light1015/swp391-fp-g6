package dao;

import config.DBConnect;
import entity.User;
import java.sql.*;

public class UserDAO {

    // Hàm lấy thông tin người dùng theo account_id
    public User getUserById(int accountId) throws ClassNotFoundException {
        String sql = "SELECT a.account_id, a.username, a.password, a.email, a.role, "
                + "u.full_name, u.phone, u.address, u.image "
                + "FROM Account a "
                + "LEFT JOIN User_Profile u ON a.account_id = u.account_id "
                + "WHERE a.account_id = ?";
        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getBoolean("role"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("image")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(int accountId, String full_name, String phone, String address, String image) throws ClassNotFoundException {
        String sql = "UPDATE User_Profile SET full_name = ?, phone = ?, address = ?, image = ? WHERE account_id = ?";
        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, full_name);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setString(4, image);
            ps.setInt(5, accountId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
