package com.clinica.client;

import com.clinica.model.CitaDTO;
import com.clinica.rmi.MedicalService;
import javax.swing.*;
import java.awt.*;

public class CitaPanel extends JPanel {
    private final MedicalService srv;
    private final CitaTableModel model = new CitaTableModel();
    private final JTable table = new JTable(model);

    public CitaPanel(MedicalService srv) {
        this.srv = srv;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel btns = new JPanel();
        JButton add = new JButton("Nueva"), edit = new JButton("Editar"), del = new JButton("Borrar");
        btns.add(add); btns.add(edit); btns.add(del);
        add(btns, BorderLayout.SOUTH);

        add.addActionListener(e -> openDialog(null));
        edit.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r>=0) openDialog(model.getCitaAt(r));
            else JOptionPane.showMessageDialog(this,"Selecciona una cita");
        });
        del.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r>=0) {
                int id = model.getCitaAt(r).getIdCita();
                try { srv.deleteCita(id); load(); }
                catch(Exception ex){ showError(ex); }
            } else JOptionPane.showMessageDialog(this,"Selecciona una cita");
        });

        load();
    }

    private void load() {
        try { model.setCitas(srv.getAllCitas()); }
        catch(Exception ex){ showError(ex); }
    }

    private void openDialog(CitaDTO c) {
        CitaDialog dlg = new CitaDialog(SwingUtilities.getWindowAncestor(this), srv, c);
        dlg.setVisible(true);
        if (dlg.isSaved()) load();
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: "+ex.getMessage());
    }
}
