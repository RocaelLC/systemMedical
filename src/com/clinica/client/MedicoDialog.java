package com.clinica.client;

import com.clinica.model.MedicoDTO;
import com.clinica.rmi.MedicalService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MedicoDialog extends JDialog {
    private final MedicalService srv;
    private final MedicoDTO medico;
    private boolean saved = false;

    private final JTextField tfNombre = new JTextField(20);
    private final JTextField tfEsp = new JTextField(20);
    private final JTextField tfCed = new JTextField(20);
    private final JTextField tfEmail = new JTextField(20);

    public MedicoDialog(Window owner, MedicalService srv, MedicoDTO m) {
        super(owner, "Médico", ModalityType.APPLICATION_MODAL);
        this.srv = srv;
        this.medico = (m!=null? m : new MedicoDTO());
        init();
        pack(); setLocationRelativeTo(owner);
    }

    private void init() {
        JPanel form = new JPanel(new GridLayout(0,2,5,5));
        form.add(new JLabel("Nombre:")); form.add(tfNombre);
        form.add(new JLabel("Especialidad:")); form.add(tfEsp);
        form.add(new JLabel("Cédula:")); form.add(tfCed);
        form.add(new JLabel("Email:")); form.add(tfEmail);

        if (medico.getIdMedico()>0) {
            tfNombre.setText(medico.getNombre());
            tfEsp.setText(medico.getEspecialidad());
            tfCed.setText(medico.getCedula());
            tfEmail.setText(medico.getEmail());
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
        // validaciones simples
        if (tfNombre.getText().isBlank()||tfEsp.getText().isBlank()) {
            JOptionPane.showMessageDialog(this,"Nombre y especialidad obligatorios");
            return;
        }
        medico.setNombre(tfNombre.getText().trim());
        medico.setEspecialidad(tfEsp.getText().trim());
        medico.setCedula(tfCed.getText().trim());
        medico.setEmail(tfEmail.getText().trim());

        try {
            if (medico.getIdMedico()>0) srv.updateMedico(medico);
            else srv.addMedico(medico);
            saved = true;
            dispose();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage());
        }
    }

    public boolean isSaved() { return saved; }
}
