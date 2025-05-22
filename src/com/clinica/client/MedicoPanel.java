package com.clinica.client;

import com.clinica.model.MedicoDTO;
import com.clinica.rmi.MedicalService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MedicoPanel extends JPanel {
    private final MedicalService srv;
    private final MedicoTableModel model = new MedicoTableModel();
    private final JTable table = new JTable(model);

    public MedicoPanel(MedicalService srv) {
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
            if (r>=0) openDialog(model.getMedicoAt(r));
            else JOptionPane.showMessageDialog(this,"Selecciona un médico");
        });
        del.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r>=0) {
                int id = model.getMedicoAt(r).getIdMedico();
                try {
                    srv.deleteMedico(id);
                    load();
                } catch(Exception ex){ showError(ex); }
            } else JOptionPane.showMessageDialog(this,"Selecciona un médico");
        });

        load();
    }

    private void load() {
        try {
            model.setMedicos(srv.getAllMedicos());
        } catch(Exception ex){ showError(ex); }
    }

    private void openDialog(MedicoDTO m) {
        MedicoDialog dlg = new MedicoDialog(SwingUtilities.getWindowAncestor(this), srv, m);
        dlg.setVisible(true);
        if (dlg.isSaved()) load();
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: "+ex.getMessage());
    }
}
