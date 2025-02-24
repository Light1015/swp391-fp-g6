/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DBConnect;
import entity.Account;
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

}
