package com.cms.util;

import java.sql.*;

public class DBConnection {
    private static Connection con;

    public static Connection getConnection() throws Exception {
        if (con == null || con.isClosed()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/complaint_system",
                "root",
                "Ashu@0019"   // ← यहाँ अपना MySQL root password डालना
            );
        }
        return con;
    }
}
