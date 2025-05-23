package com.clinica.client;

import com.clinica.model.MedicoDTO;
import com.clinica.rmi.MedicalService;
import java.awt.*;
import javax.swing.*;

public class MedicoPanel extends JPanel {
    private final MedicalService srv;
    private final MedicoTableModel model = new MedicoTableModel();
    private final JTable table = new JTable(model);

    public MedicoPanel(MedicalService srv) {
        this.srv = srv;
        setLayout(new BorderLayout());

        // Tabla de médicos
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel de botones
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

        // Listeners
        btnNew.addActionListener(e -> openDialog(null));
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) openDialog(model.getMedicoAt(row));
            else JOptionPane.showMessageDialog(this, "Selecciona un médico");
        });
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = model.getMedicoAt(row).getIdMedico();
                try {
                    srv.deleteMedico(id);
                    load();
                } catch (Exception ex) {
                    showError(ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un médico");
            }
        });
        btnRefresh.addActionListener(e -> {
            System.out.println("[Client] Botón Actualizar pulsado");
            load();
        });

        // Carga inicial
        load();
    }

    private void load() {
        System.out.println("[Client] load(): llamando getAllMedicos()");
        try {
            model.setMedicos(srv.getAllMedicos());
        } catch (Exception ex) {
            showError(ex);
        }
    }

    private void openDialog(MedicoDTO m) {
        MedicoDialog dlg = new MedicoDialog(
            SwingUtilities.getWindowAncestor(this),
            srv,
            m
        );
        dlg.setVisible(true);
        System.out.println("[Client] openDialog(): dialog closed, saved = " + dlg.isSaved());
        if (dlg.isSaved()) {
            System.out.println("[Client] openDialog(): recargando tabla");
            load();
        }
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
}
