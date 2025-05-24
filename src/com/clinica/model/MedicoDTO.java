package com.clinica.model;

import java.io.Serializable;

public class MedicoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idMedico;
    private String nombre;
    private String especialidad;
    private String cedula;
    private String email;

    public MedicoDTO() { }

    public MedicoDTO(int idMedico, String nombre, String especialidad, String cedula, String email) {
        this.idMedico    = idMedico;
        this.nombre      = nombre;
        this.especialidad= especialidad;
        this.cedula      = cedula;
        this.email       = email;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return idMedico + " - " + nombre;
    }
}
