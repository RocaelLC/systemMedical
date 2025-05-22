package com.clinica.server;
import com.clinica.model.*;
import com.clinica.rmi.MedicalService;
import com.clinica.util.DBUtil;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.*;

public class MedicalServiceImpl extends UnicastRemoteObject implements MedicalService {
    public MedicalServiceImpl() throws RemoteException { super(); }

    // --- MÉDICO ---
    @Override
    public void addMedico(MedicoDTO m) throws RemoteException {
        String sql = "INSERT INTO Medico(nombre, especialidad, cedula, email) VALUES(?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getEspecialidad());
            ps.setString(3, m.getCedula());
            ps.setString(4, m.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RemoteException(e.getMessage(), e); }
    }
    @Override
    public List<MedicoDTO> getAllMedicos() throws RemoteException { /* idéntico al ejemplo */
        return null;
    }

    @Override
    public void updateMedico(MedicoDTO m) throws RemoteException {
        String sql = "UPDATE Medico SET nombre=?, especialidad=?, cedula=?, email=? WHERE idMedico=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getEspecialidad());
            ps.setString(3, m.getCedula());
            ps.setString(4, m.getEmail());
            ps.setInt(5, m.getIdMedico());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RemoteException(e.getMessage(), e); }
    }
    @Override
    public void deleteMedico(int idMedico) throws RemoteException {
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM Medico WHERE idMedico=?")) {
            ps.setInt(1, idMedico);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RemoteException(e.getMessage(), e); }
    }

    // --- PACIENTE (mismo patrón que MEDICO) ---
    @Override
    public void addPaciente(PacienteDTO p) throws RemoteException { /* INSERT INTO Paciente(...) */ }
    @Override
    public List<PacienteDTO> getAllPacientes() throws RemoteException { /* SELECT * FROM Paciente */
        return null;
    }
    @Override
    public void updatePaciente(PacienteDTO p) throws RemoteException { /* UPDATE Paciente SET ... */ }
    @Override
    public void deletePaciente(int idPaciente) throws RemoteException { /* DELETE FROM Paciente */ }

    // --- CITA ---
    @Override
    public void addCita(CitaDTO c) throws RemoteException { /* INSERT INTO Cita(...) */ }
    @Override
    public List<CitaDTO> getAllCitas() throws RemoteException { /* SELECT * FROM Cita */
        return null;
    }
    @Override
    public void updateCita(CitaDTO c) throws RemoteException { /* UPDATE Cita SET ... */ }
    @Override
    public void deleteCita(int idCita) throws RemoteException { /* DELETE FROM Cita */ }
}
