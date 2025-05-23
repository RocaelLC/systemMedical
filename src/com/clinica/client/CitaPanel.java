package com.clinica.client;

import com.clinica.model.CitaDTO;
import com.clinica.rmi.MedicalService;
import java.awt.*;
import javax.swing.*;

public class CitaPanel extends JPanel {
    private final MedicalService srv;
    private final CitaTableModel model = new CitaTableModel();
    private final JTable table = new JTable(model);

    public CitaPanel(MedicalService srv) {
        this.srv = srv;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton btnNew     = new JButton("Nueva");
        JButton btnEdit    = new JButton("Editar");
        JButton btnDelete  = new JButton("Borrar");
        JButton btnRefresh = new JButton("Actualizar");

        btns.add(btnNew);
        btns.add(btnEdit);
        btns.add(btnDelete);
        btns.add(btnRefresh);
        add(btns, BorderLayout.SOUTH);

        btnNew.addActionListener(e -> openDialog(null));
        btnEdit.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) openDialog(model.getCitaAt(r));
            else JOptionPane.showMessageDialog(this, "Selecciona una cita");
        });
        btnDelete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                int id = model.getCitaAt(r).getIdCita();
                try {
                    srv.deleteCita(id);
                    load();
                } catch (Exception ex) { showError(ex); }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una cita");
            }
        });
        btnRefresh.addActionListener(e -> {
            System.out.println("[Client Cita] Botón Actualizar pulsado");
            load();
        });

        load();
    }

    private void load() {
        System.out.println("[Client Cita] load(): llamando getAllCitas()");
        try {
            model.setCitas(srv.getAllCitas());
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void openDialog(CitaDTO c) {
        CitaDialog dlg = new CitaDialog(
            SwingUtilities.getWindowAncestor(this),
            srv,
            c
        );
        dlg.setVisible(true);
        System.out.println("[Client Cita] openDialog(): saved = " + dlg.isSaved());
        if (dlg.isSaved()) {
            System.out.println("[Client Cita] openDialog(): recargando tabla");
            load();
        }
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
}
