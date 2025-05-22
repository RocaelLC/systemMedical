package com.clinica.rmi;
import com.clinica.model.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MedicalService extends Remote {
    // MÉDICO
    void addMedico(MedicoDTO m) throws RemoteException;
    List<MedicoDTO> getAllMedicos() throws RemoteException;
    void updateMedico(MedicoDTO m) throws RemoteException;
    void deleteMedico(int idMedico) throws RemoteException;

    // PACIENTE
    void addPaciente(PacienteDTO p) throws RemoteException;
    List<PacienteDTO> getAllPacientes() throws RemoteException;
    void updatePaciente(PacienteDTO p) throws RemoteException;
    void deletePaciente(int idPaciente) throws RemoteException;

    // CITA
    void addCita(CitaDTO c) throws RemoteException;
    List<CitaDTO> getAllCitas() throws RemoteException;
    void updateCita(CitaDTO c) throws RemoteException;
    void deleteCita(int idCita) throws RemoteException;
}
