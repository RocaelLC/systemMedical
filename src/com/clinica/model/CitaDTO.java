package com.clinica.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class CitaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idCita;
    private Date fecha;
    private Time hora;
    private String motivo;
    private int idMedico;
    private int idPaciente;

    public CitaDTO() { }

    public CitaDTO(int idCita, Date fecha, Time hora, String motivo, int idMedico, int idPaciente) {
        this.idCita    = idCita;
        this.fecha     = fecha;
        this.hora      = hora;
        this.motivo    = motivo;
        this.idMedico  = idMedico;
        this.idPaciente= idPaciente;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Override
    public String toString() {
        return idCita + ": " + fecha + " " + hora;
    }
}
