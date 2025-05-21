// src/com/clinica/model/MedicoDTO.java
package com.clinica.model;
import java.io.Serializable;

public class MedicoDTO implements Serializable {
  private int idMedico;
  private String nombre, especialidad, cedula, email;
  // getters/setters y constructor vacío
}

// PacienteDTO y CitaDTO similares,
// en com.clinica.model, con campos acordes a la tabla.

// src/com/clinica/util/DBUtil.java
package com.clinica.util;
import java.sql.*;

public class DBUtil {
  private static final String URL = "jdbc:mysql://localhost:3306/clinica";
  private static final String USER = "root", PASS = "tu_password";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASS);
  }
}
