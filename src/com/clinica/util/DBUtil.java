package com.clinica.util;

import java.sql.*;

public class DBUtil {
    static {
  try {
    System.out.println("[DBUtil] Cargando driver MySQL...");
    Class.forName("com.mysql.cj.jdbc.Driver");
    System.out.println("[DBUtil] Driver cargado con éxito.");
  } catch (ClassNotFoundException e) {
    e.printStackTrace();  // muestra la causa real
    throw new ExceptionInInitializerError(e);
  }
}


    private static final String URL  = "jdbc:mysql://localhost:3306/clinica";
    private static final String USER = "root";
    private static final String PASS = "030603";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
