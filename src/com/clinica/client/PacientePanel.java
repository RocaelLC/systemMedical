package com.clinica.client;

import com.clinica.model.PacienteDTO;
import com.clinica.rmi.MedicalService;
import javax.swing.*;
import java.awt.*;

public class PacientePanel extends JPanel {
    private final MedicalService srv;
    private final PacienteTableModel model = new PacienteTableModel();
    private final JTable table = new JTable(model);

    public PacientePanel(MedicalService srv) {
        this.srv = srv;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel btns = new JPanel();
        JButton add = new JButton("Nuevo"), edit = new JButton("Editar"), del = new JButton("Borrar");
        btns.add(add); btns.add(edit); btns.add(del);
        add(btns, BorderLayout.SOUTH);

        add.addActionListener(e -> openDialog(null));
        edit.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r>=0) openDialog(model.getPacienteAt(r));
            else JOptionPane.showMessageDialog(this,"Selecciona un paciente");
        });
        del.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r>=0) {
                int id = model.getPacienteAt(r).getIdPaciente();
                try { srv.deletePaciente(id); load(); }
                catch(Exception ex){ showError(ex); }
            } else JOptionPane.showMessageDialog(this,"Selecciona un paciente");
        });

        load();
    }

    private void load() {
        try { model.setPacientes(srv.getAllPacientes()); }
        catch(Exception ex){ showError(ex); }
    }

    private void openDialog(PacienteDTO p) {
        PacienteDialog dlg = new PacienteDialog(SwingUtilities.getWindowAncestor(this), srv, p);
        dlg.setVisible(true);
        if (dlg.isSaved()) load();
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: "+ex.getMessage());
    }
}
