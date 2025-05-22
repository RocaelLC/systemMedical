package com.clinica.client;

import com.clinica.model.CitaDTO;
import com.clinica.model.MedicoDTO;
import com.clinica.model.PacienteDTO;
import com.clinica.rmi.MedicalService;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.swing.*;

public class CitaDialog extends JDialog {
    private final MedicalService srv;
    private final CitaDTO cita;
    private boolean saved = false;

    private final JTextField tfFecha = new JTextField(10);   // yyyy-MM-dd
    private final JTextField tfHora  = new JTextField(8);    // HH:mm:ss
    private final JTextField tfMot   = new JTextField(20);
    private final JComboBox<MedicoDTO> cbMed  = new JComboBox<>();
    private final JComboBox<PacienteDTO> cbPac = new JComboBox<>();

    public CitaDialog(Window owner, MedicalService srv, CitaDTO c) {
        super(owner, "Cita", ModalityType.APPLICATION_MODAL);
        this.srv = srv;
        this.cita = (c!=null? c : new CitaDTO());
        init();
        pack(); setLocationRelativeTo(owner);
    }

    private void init() {
        // carga médicos y pacientes
        try {
            List<MedicoDTO> ms = srv.getAllMedicos();
            for (MedicoDTO m: ms) cbMed.addItem(m);
            List<PacienteDTO> ps = srv.getAllPacientes();
            for (PacienteDTO p: ps) cbPac.addItem(p);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando listas: "+e.getMessage());
        }

        JPanel form = new JPanel(new GridLayout(0,2,5,5));
        form.add(new JLabel("Fecha (YYYY-MM-DD):")); form.add(tfFecha);
        form.add(new JLabel("Hora (HH:MM:SS):"));      form.add(tfHora);
        form.add(new JLabel("Motivo:"));               form.add(tfMot);
        form.add(new JLabel("Médico:"));               form.add(cbMed);
        form.add(new JLabel("Paciente:"));             form.add(cbPac);

        if (cita.getIdCita()>0) {
            tfFecha.setText(cita.getFecha().toString());
            tfHora.setText(cita.getHora().toString());
            tfMot.setText(cita.getMotivo());
            // seleccionar items
            for (int i=0;i<cbMed.getItemCount();i++)
                if (cbMed.getItemAt(i).getIdMedico()==cita.getIdMedico())
                    cbMed.setSelectedIndex(i);
            for (int i=0;i<cbPac.getItemCount();i++)
                if (cbPac.getItemAt(i).getIdPaciente()==cita.getIdPaciente())
                    cbPac.setSelectedIndex(i);
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
        try {
            LocalDate ld = LocalDate.parse(tfFecha.getText().trim());
            LocalTime lt = LocalTime.parse(tfHora.getText().trim());
            cita.setFecha(Date.valueOf(ld));
            cita.setHora(Time.valueOf(lt));
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this,"Formato de fecha/hora inválido");
            return;
        }
        cita.setMotivo(tfMot.getText().trim());
        MedicoDTO m = (MedicoDTO)cbMed.getSelectedItem();
        PacienteDTO p = (PacienteDTO)cbPac.getSelectedItem();
        if (m==null||p==null) {
            JOptionPane.showMessageDialog(this,"Debes seleccionar médico y paciente");
            return;
        }
        cita.setIdMedico(m.getIdMedico());
        cita.setIdPaciente(p.getIdPaciente());

        try {
            if (cita.getIdCita()>0) srv.updateCita(cita);
            else srv.addCita(cita);
            saved = true;
            dispose();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage());
        }
    }

    public boolean isSaved() { return saved; }
}
