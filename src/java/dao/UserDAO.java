package dao;

import config.DBConnect;
import entity.User;
import java.sql.*;

public class UserDAO {

    // Hàm lấy thông tin người dùng theo account_id
    public User getUserById(int accountId) throws ClassNotFoundException {
        String sql = "SELECT a.account_id, a.username, a.email, a.password, a.role, "
                + "u.full_name, u.phone, u.address, u.image, u.is_member "
                + "FROM Account a LEFT JOIN User_Profile u ON a.account_id = u.account_id "
                + "WHERE a.account_id = ?";

        // Kết nối và thực hiện truy vấn
        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);  // Gán giá trị accountId vào truy vấn
            ResultSet rs = ps.executeQuery();

            // Kiểm tra nếu có kết quả trả về
            if (rs.next()) {
                return new User(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getBoolean("role"), // Lấy thông tin role (admin/user)
                        rs.getString("full_name") != null ? rs.getString("full_name") : "",
                        rs.getString("phone") != null ? rs.getString("phone") : "",
                        rs.getString("address") != null ? rs.getString("address") : "",
                        rs.getString("image") != null ? rs.getString("image") : "",
                        rs.getBoolean("is_member")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Nếu không tìm thấy người dùng
    }

    public boolean updateUser(int accountId, String fullName, String phone, String address, String image) throws ClassNotFoundException {
        String sql = "UPDATE User_Profile SET full_name = ?, phone = ?, address = ?, image = ? WHERE account_id = ?";
        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fullName);
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
