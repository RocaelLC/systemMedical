package com.clinica.client;

import com.clinica.model.MedicoDTO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MedicoTableModel extends AbstractTableModel {
    private List<MedicoDTO> lista = new ArrayList<>();
    private final String[] cols = {"ID","Nombre","Especialidad","Cédula","Email"};

    public void setMedicos(List<MedicoDTO> lst) {
        this.lista = lst;
        fireTableDataChanged();
    }

    public MedicoDTO getMedicoAt(int row) {
        return lista.get(row);
    }

    @Override
public int getRowCount() {
    return (lista == null) ? 0 : lista.size();
}

    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int c) { return cols[c]; }

    @Override
    public Object getValueAt(int r, int c) {
        MedicoDTO m = lista.get(r);
        switch(c) {
            case 0: return m.getIdMedico();
            case 1: return m.getNombre();
            case 2: return m.getEspecialidad();
            case 3: return m.getCedula();
            case 4: return m.getEmail();
            default: return "";
        }
    }
}
