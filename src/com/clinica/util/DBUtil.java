package com.clinica.util;

import java.sql.*;

public class DBUtil {
    static {
  try {
    Class.forName("com.mysql.cj.jdbc.Driver");
} catch (Exception e) {
    e.printStackTrace();
    throw new ExceptionInInitializerError(e);
}

}


    private static final String URL = 
    "jdbc:mysql://localhost:3306/clinica?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASS = "030603";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
