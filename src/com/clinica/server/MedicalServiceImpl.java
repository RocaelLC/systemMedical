// src/com/clinica/server/MedicalServiceImpl.java
package com.clinica.server;

import com.clinica.rmi.MedicalService;
import com.clinica.model.*;
import com.clinica.util.DBUtil;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalServiceImpl extends UnicastRemoteObject implements MedicalService {
  protected MedicalServiceImpl() throws RemoteException { super(); }

  // Ejemplo: CRUD Médico
  @Override
  public void addMedico(MedicoDTO m) throws RemoteException {
    String sql = "INSERT INTO Medico(nombre, especialidad, cedula, email) VALUES (?,?,?,?)";
    try (Connection c = DBUtil.getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setString(1, m.getNombre());
      ps.setString(2, m.getEspecialidad());
      ps.setString(3, m.getCedula());
      ps.setString(4, m.getEmail());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RemoteException("Error al agregar médico", e);
    }
  }

  @Override
  public List<MedicoDTO> getAllMedicos() throws RemoteException {
    List<MedicoDTO> lista = new ArrayList<>();
    String sql = "SELECT * FROM Medico";
    try (Connection c = DBUtil.getConnection();
         Statement st = c.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
      while (rs.next()) {
        MedicoDTO m = new MedicoDTO();
        m.setIdMedico(rs.getInt("idMedico"));
        m.setNombre(rs.getString("nombre"));
        m.setEspecialidad(rs.getString("especialidad"));
        m.setCedula(rs.getString("cedula"));
        m.setEmail(rs.getString("email"));
        lista.add(m);
      }
    } catch (SQLException e) {
      throw new RemoteException("Error al obtener médicos", e);
    }
    return lista;
  }

  // Implementar updateMedico, deleteMedico...
  // Luego CRUD de Paciente y Cita de forma análoga.
}
