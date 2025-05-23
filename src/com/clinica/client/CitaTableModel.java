package com.clinica.client;

import com.clinica.model.CitaDTO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CitaTableModel extends AbstractTableModel {
    // Inicializamos siempre la lista para evitar NPE
    private List<CitaDTO> lista = new ArrayList<>();
    private final String[] cols = {"ID","Fecha","Hora","Motivo","ID Médico","ID Paciente"};

    public void setCitas(List<CitaDTO> lst) {
        this.lista = lst;
        fireTableDataChanged();
    }

    public CitaDTO getCitaAt(int row) {
        return lista.get(row);
    }

    @Override
    public int getRowCount() {
        return lista == null ? 0 : lista.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int c) {
        return cols[c];
    }

    @Override
    public Object getValueAt(int r, int c) {
        CitaDTO cta = lista.get(r);
        switch(c) {
            case 0: return cta.getIdCita();
            case 1: return cta.getFecha().toString();
            case 2: return cta.getHora().toString();
            case 3: return cta.getMotivo();
            case 4: return cta.getIdMedico();
            case 5: return cta.getIdPaciente();
            default: return "";
        }
    }
}