package com.clinica.client;
import com.clinica.rmi.MedicalService;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private MedicalService srv;
    public MainFrame(MedicalService srv) {
        this.srv = srv;
        setTitle("Clínica RMI");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
    }
    private void initUI() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Médicos", new MedicoPanel(srv));
        tabs.addTab("Pacientes", new PacientePanel(srv));
        tabs.addTab("Citas",    new CitaPanel(srv));
        add(tabs, BorderLayout.CENTER);
    }
}
