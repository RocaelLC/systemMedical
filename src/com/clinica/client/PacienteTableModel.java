package com.clinica.client;

import com.clinica.model.PacienteDTO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PacienteTableModel extends AbstractTableModel {
    private List<PacienteDTO> lista = new ArrayList<>();
    private final String[] cols = {"ID","Nombre","CURP","Teléfono","Email"};

    public void setPacientes(List<PacienteDTO> lst) {
        this.lista = lst;
        fireTableDataChanged();
    }

    public PacienteDTO getPacienteAt(int row) {
        return lista.get(row);
    }

   @Override
public int getRowCount() {
    return lista == null ? 0 : lista.size();
}

    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int c) { return cols[c]; }

    @Override
    public Object getValueAt(int r, int c) {
        PacienteDTO p = lista.get(r);
        switch(c) {
            case 0: return p.getIdPaciente();
            case 1: return p.getNombre();
            case 2: return p.getCurp();
            case 3: return p.getTelefono();
            case 4: return p.getEmail();
            default: return "";
        }
    }
}
