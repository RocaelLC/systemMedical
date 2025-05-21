// src/com/clinica/client/MainFrame.java
package com.clinica.client;

import com.clinica.rmi.MedicalService;
import com.clinica.model.MedicoDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
  private MedicalService srv;
  private JTable tblMedicos;
  private MedicoTableModel modelMedicos;

  public MainFrame(MedicalService srv) {
    this.srv = srv;
    setTitle("Clínica - Sistema Distribuido");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(800,600);
    initComponents();
    loadMedicos();
  }

  private void initComponents() {
    modelMedicos = new MedicoTableModel();
    tblMedicos = new JTable(modelMedicos);
    JButton btnAdd = new JButton("Nuevo Médico");
    btnAdd.addActionListener(e -> openAddMedicoDialog());
    add(new JScrollPane(tblMedicos), BorderLayout.CENTER);
    add(btnAdd, BorderLayout.SOUTH);
  }

  private void loadMedicos() {
    try {
      modelMedicos.setMedicos(srv.getAllMedicos());
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
  }

  private void openAddMedicoDialog() {
    MedicoDialog dlg = new MedicoDialog(this, srv, null);
    dlg.setVisible(true);
    if (dlg.isSaved()) loadMedicos();
  }

  // Similar para Editar y Borrar, y paneles/tabbed panes para Paciente y Cita.
}
