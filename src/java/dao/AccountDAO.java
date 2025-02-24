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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class AccountDAO {

    Connection conn;

    /**
     *
     */
    public AccountDAO() {
        try {
            conn = DBConnect.connect();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    PreparedStatement ps = null;
    ResultSet rs = null;

    public Account login(String username, String password) {
        String query = "SELECT * FROM Account WHERE username = ? AND password = ?";
        Account ac = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(0, username);
            ps.setString(1, password);
            rs = ps.executeQuery();
            while (rs.next()) {
                ac = new Account(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getInt("isAdmin"),
                        rs.getInt("isUser"));
            }
        } catch (Exception e) {
        }
        return ac;
    }

}
