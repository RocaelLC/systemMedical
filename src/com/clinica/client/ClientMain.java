// src/com/clinica/client/ClientMain.java
package com.clinica.client;

import com.clinica.rmi.MedicalService;
import javax.swing.*;
import java.rmi.Naming;

public class ClientMain {
  public static void main(String[] args) {
    try {
      MedicalService srv = (MedicalService) Naming.lookup("rmi://localhost/MedService");
      SwingUtilities.invokeLater(() -> new MainFrame(srv).setVisible(true));
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "No se pudo conectar al servidor: " + e.getMessage());
    }
  }
}
