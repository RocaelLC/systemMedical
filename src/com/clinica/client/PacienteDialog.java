package com.clinica.client;

import com.clinica.model.PacienteDTO;
import com.clinica.rmi.MedicalService;
import javax.swing.*;
import java.awt.*;

public class PacienteDialog extends JDialog {
    private final MedicalService srv;
    private final PacienteDTO paciente;
    private boolean saved = false;

    private final JTextField tfNombre = new JTextField(20);
    private final JTextField tfCurp   = new JTextField(20);
    private final JTextField tfTel    = new JTextField(20);
    private final JTextField tfEmail  = new JTextField(20);

    public PacienteDialog(Window owner, MedicalService srv, PacienteDTO p) {
        super(owner, "Paciente", ModalityType.APPLICATION_MODAL);
        this.srv = srv;
        this.paciente = (p!=null? p : new PacienteDTO());
        init();
        pack(); setLocationRelativeTo(owner);
    }

    private void init() {
        JPanel form = new JPanel(new GridLayout(0,2,5,5));
        form.add(new JLabel("Nombre:")); form.add(tfNombre);
        form.add(new JLabel("CURP:"));   form.add(tfCurp);
        form.add(new JLabel("Teléfono:"));form.add(tfTel);
        form.add(new JLabel("Email:"));   form.add(tfEmail);

        if (paciente.getIdPaciente()>0) {
            tfNombre.setText(paciente.getNombre());
            tfCurp.setText(paciente.getCurp());
            tfTel.setText(paciente.getTelefono());
            tfEmail.setText(paciente.getEmail());
        }

        JButton btnSave = new JButton("Guardar");
        btnSave.addActionListener(e -> onSave());
        JButton btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(e -> dispose());

        JPanel south = new JPanel();
        south.add(btnSave); south.add(btnCancel);

        getContentPane().add(form, BorderLayout.CENTER);
        getContentPane().add(south, BorderLayout.SOUTH);
    }

    private void onSave() {
        if (tfNombre.getText().isBlank()||tfCurp.getText().isBlank()) {
            JOptionPane.showMessageDialog(this,"Nombre y CURP obligatorios");
            return;
        }
        paciente.setNombre(tfNombre.getText().trim());
        paciente.setCurp(tfCurp.getText().trim());
        paciente.setTelefono(tfTel.getText().trim());
        paciente.setEmail(tfEmail.getText().trim());

        try {
            if (paciente.getIdPaciente()>0) srv.updatePaciente(paciente);
            else srv.addPaciente(paciente);
            saved = true;
            dispose();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage());
        }
    }

    public boolean isSaved() { return saved; }
}
