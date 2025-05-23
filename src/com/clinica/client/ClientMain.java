package com.clinica.client;
import com.clinica.rmi.MedicalService;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.*;

public class ClientMain {
    public static void main(String[] args) {
        try {
            MedicalService srv = (MedicalService) Naming.lookup("rmi://localhost/MedService");
            SwingUtilities.invokeLater(() -> {
                new MainFrame(srv).setVisible(true);
            });
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar: " + e.getMessage());
        }
    }
    
}
