package com.clinica.client;

import com.clinica.model.PacienteDTO;
import com.clinica.rmi.MedicalService;
import java.awt.*;
import javax.swing.*;

public class PacientePanel extends JPanel {
    private final MedicalService srv;
    private final PacienteTableModel model = new PacienteTableModel();
    private final JTable table = new JTable(model);

    public PacientePanel(MedicalService srv) {
        this.srv = srv;
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton btnNew     = new JButton("Nuevo");
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
            if (r >= 0) openDialog(model.getPacienteAt(r));
            else JOptionPane.showMessageDialog(this, "Selecciona un paciente");
        });
        btnDelete.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                int id = model.getPacienteAt(r).getIdPaciente();
                try {
                    srv.deletePaciente(id);
                    load();
                } catch (Exception ex) { showError(ex); }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un paciente");
            }
        });
        btnRefresh.addActionListener(e -> {
            System.out.println("[Client Paciente] Botón Actualizar pulsado");
            load();
        });

        load();
    }

    private void load() {
        System.out.println("[Client Paciente] load(): llamando getAllPacientes()");
        try {
            model.setPacientes(srv.getAllPacientes());
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void openDialog(PacienteDTO p) {
        PacienteDialog dlg = new PacienteDialog(
            SwingUtilities.getWindowAncestor(this),
            srv,
            p
        );
        dlg.setVisible(true);
        System.out.println("[Client Paciente] openDialog(): saved = " + dlg.isSaved());
        if (dlg.isSaved()) {
            System.out.println("[Client Paciente] openDialog(): recargando tabla");
            load();
        }
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
}
