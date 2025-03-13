package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

//    private final String userID = "sa";
//    private final String password = "1";
//    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=BookReadingWed;encrypt=true;trustServerCertificate=true;";

    public static Connection connect() throws ClassNotFoundException, SQLException {
        //Khai bao driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //Tao doi tuong Connection
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=BookWed;user=sa;password=1;encrypt=true;trustServerCertificate=true;");
        return conn;
    }
//
//    // Phương thức kiểm tra kết nối
//    public void testConnection() {
//        try ( Connection conn = connect()) {
//            if (conn != null) {
//                System.out.println("Kết nối đến database thành công!");
//            } else {
//                System.out.println("Kết nối thất bại!");
//            }
//        } catch (Exception e) {
//            System.err.println("Lỗi kết nối: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        DBConnect db = new DBConnect();
//        db.testConnection();
//    }
}
