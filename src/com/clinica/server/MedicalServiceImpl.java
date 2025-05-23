package com.clinica.server;

import com.clinica.model.CitaDTO;
import com.clinica.model.MedicoDTO;
import com.clinica.model.PacienteDTO;
import com.clinica.rmi.MedicalService;
import com.clinica.util.DBUtil;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalServiceImpl extends UnicastRemoteObject implements MedicalService {

    public MedicalServiceImpl() throws RemoteException {
        super();
    }

    // --- MÉDICO ---

    @Override
    public void addMedico(MedicoDTO m) throws RemoteException {
        System.out.println("[Server] addMedico recibido: " + m.getNombre());
        String sql = "INSERT INTO Medico(nombre, especialidad, cedula, email) VALUES (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getEspecialidad());
            ps.setString(3, m.getCedula());
            ps.setString(4, m.getEmail());
            int filas = ps.executeUpdate();
            System.out.println("[Server] Filas afectadas (Medico): " + filas);
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
            throw new RemoteException("Error al obtener médicos", e);
        }
        return lista;
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
            int filas = ps.executeUpdate();
            System.out.println("[Server] Filas afectadas (updateMedico): " + filas);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error al actualizar médico", e);
        }
    }

    @Override
    public void deleteMedico(int idMedico) throws RemoteException {
        String sql = "DELETE FROM Medico WHERE idMedico=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idMedico);
            int filas = ps.executeUpdate();
            System.out.println("[Server] Filas afectadas (deleteMedico): " + filas);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error al borrar médico", e);
        }
    }

    // --- PACIENTE ---

    @Override
    public void addPaciente(PacienteDTO p) throws RemoteException {
        System.out.println("[Server] addPaciente recibido: " + p.getNombre());
        String sql = "INSERT INTO Paciente(nombre, curp, telefono, email) VALUES (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCurp());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getEmail());
            int filas = ps.executeUpdate();
            System.out.println("[Server] Filas afectadas (Paciente): " + filas);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error al agregar paciente", e);
        }
    }

    @Override
    public List<PacienteDTO> getAllPacientes() throws RemoteException {
        List<PacienteDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM Paciente";
        try (Connection c = DBUtil.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                PacienteDTO p = new PacienteDTO();
                p.setIdPaciente(rs.getInt("idPaciente"));
                p.setNombre(rs.getString("nombre"));
                p.setCurp(rs.getString("curp"));
                p.setTelefono(rs.getString("telefono"));
                p.setEmail(rs.getString("email"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error al obtener pacientes", e);
        }
        return lista;
    }

    @Override
    public void updatePaciente(PacienteDTO p) throws RemoteException {
        String sql = "UPDATE Paciente SET nombre=?, curp=?, telefono=?, email=? WHERE idPaciente=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCurp());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getEmail());
            ps.setInt(5, p.getIdPaciente());
            int filas = ps.executeUpdate();
            System.out.println("[Server] Filas afectadas (updatePaciente): " + filas);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error al actualizar paciente", e);
        }
    }

    @Override
    public void deletePaciente(int idPaciente) throws RemoteException {
        String sql = "DELETE FROM Paciente WHERE idPaciente=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            int filas = ps.executeUpdate();
            System.out.println("[Server] Filas afectadas (deletePaciente): " + filas);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error al borrar paciente", e);
        }
    }

    // --- CITA ---

    @Override
    public void addCita(CitaDTO cta) throws RemoteException {
        System.out.println("[Server] addCita recibido: " + cta.getMotivo());
        String sql = "INSERT INTO Cita(fecha, hora, motivo, idMedico, idPaciente) VALUES (?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, cta.getFecha());
            ps.setTime(2, cta.getHora());
            ps.setString(3, cta.getMotivo());
            ps.setInt(4, cta.getIdMedico());
            ps.setInt(5, cta.getIdPaciente());
            int filas = ps.executeUpdate();
            System.out.println("[Server] Filas afectadas (Cita): " + filas);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error al agregar cita", e);
        }
    }

    @Override
    public List<CitaDTO> getAllCitas() throws RemoteException {
        List<CitaDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cita";
        try (Connection c = DBUtil.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                CitaDTO cta = new CitaDTO();
                cta.setIdCita(rs.getInt("idCita"));
                cta.setFecha(rs.getDate("fecha"));
                cta.setHora(rs.getTime("hora"));
                cta.setMotivo(rs.getString("motivo"));
                cta.setIdMedico(rs.getInt("idMedico"));
                cta.setIdPaciente(rs.getInt("idPaciente"));
                lista.add(cta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error al obtener citas", e);
        }
        return lista;
    }

    @Override
    public void updateCita(CitaDTO cta) throws RemoteException {
        String sql = "UPDATE Cita SET fecha=?, hora=?, motivo=?, idMedico=?, idPaciente=? WHERE idCita=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, cta.getFecha());
            ps.setTime(2, cta.getHora());
            ps.setString(3, cta.getMotivo());
            ps.setInt(4, cta.getIdMedico());
            ps.setInt(5, cta.getIdPaciente());
            ps.setInt(6, cta.getIdCita());
            int filas = ps.executeUpdate();
            System.out.println("[Server] Filas afectadas (updateCita): " + filas);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error al actualizar cita", e);
        }
    }

    @Override
    public void deleteCita(int idCita) throws RemoteException {
        String sql = "DELETE FROM Cita WHERE idCita=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idCita);
            int filas = ps.executeUpdate();
            System.out.println("[Server] Filas afectadas (deleteCita): " + filas);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error al borrar cita", e);
        }
    }
}
