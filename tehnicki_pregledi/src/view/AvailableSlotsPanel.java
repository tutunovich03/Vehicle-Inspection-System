package view;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

import controller.VlasnikController;
import model.Vlasnik;
import model.Vozilo;
import model.VoziloTip;

class AvailableSlotsPanel extends JPanel {

    private JComboBox<VoziloTip> vehicleCombo;
    private JTable table;
    private DefaultTableModel model;
    private JSpinner dateSpinner;
    private JFormattedTextField regDateField;
    private Vozilo v;
    
    private Date minDate;
    private Date initDate;

    public AvailableSlotsPanel(ClientPanel parent, VlasnikController controller, Vlasnik client) {

        setLayout(new BorderLayout());

        // TOP
        JPanel top = new JPanel();
        
        vehicleCombo = new JComboBox<>(VoziloTip.values());
        
        Calendar minCal = Calendar.getInstance();
        minCal.add(Calendar.DAY_OF_MONTH, -1);

        minDate = new Date(minCal.getTimeInMillis());

        Calendar maxCal = Calendar.getInstance();
        maxCal.add(Calendar.DAY_OF_MONTH, 15);

        Date maxDate = new Date(maxCal.getTimeInMillis());
        
        System.out.println(minDate);
        System.out.println(maxDate);
        LocalDate initial = LocalDate.now();
        
        initDate = java.sql.Date.valueOf(initial);

        SpinnerDateModel model_spiner = new SpinnerDateModel(
            initDate,   // initial value
            minDate,   // minimum value
            maxDate,   // maximum value
            Calendar.DAY_OF_MONTH
        );

        dateSpinner = new JSpinner(model_spiner);
        
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(editor);
        
        JButton loadBtn = new JButton("Load Slots");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        regDateField = new JFormattedTextField(dateFormat);
        regDateField.setColumns(10);
        regDateField.setValue(new java.util.Date()); // optional default today

        top.add(new JLabel("Istek registracije:"));
        top.add(regDateField);

        top.add(new JLabel("Vehicle:"));
        top.add(vehicleCombo);
        top.add(new JLabel("Date:"));
        top.add(dateSpinner);
        top.add(loadBtn);

        add(top, BorderLayout.NORTH);

        // TABLE
        model = new DefaultTableModel(new String[]{"Date", "Time"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // BOTTOM
        JPanel bottom = new JPanel();
        JButton scheduleBtn = new JButton("Schedule");
        JButton backBtn = new JButton("Back");

        bottom.add(scheduleBtn);
        bottom.add(backBtn);

        add(bottom, BorderLayout.SOUTH);

        // LOAD ACTION
        loadBtn.addActionListener(e -> {
            loadSlots(controller);
            
        });

        // SCHEDULE ACTION
        scheduleBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            
            LocalDate date = (LocalDate) model.getValueAt(row, 0);
            LocalTime time = (LocalTime) model.getValueAt(row, 1);

            controller.addTerminVozilo(time, date, v, client);

            JOptionPane.showMessageDialog(this, "Scheduled!");
            
            loadSlots(controller);
        });

        backBtn.addActionListener(e -> {
        	resetPanel();
        	parent.showPanel("MAIN");
        	
        });
    }

    private void loadSlots(VlasnikController controller) {
    	VoziloTip tip = (VoziloTip)vehicleCombo.getSelectedItem();
        LocalDate date = convertToLocalDate((java.util.Date) dateSpinner.getValue());
        
        LocalDate regIstek = convertToLocalDate((java.util.Date) regDateField.getValue());
        
        if(regIstek.isAfter(date.plusDays(30))) {
        	JOptionPane.showMessageDialog(
                    this,
                    "Tehnicki pregled se ne moze zakazati vise od 30 dana pre isteka registracije.",
                    "Obavestenje",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
        }
        
        if(date.isAfter(LocalDate.now().plusDays(15)) /*|| date.isBefore(LocalDate.now().plusDays(3))*/) {
        	System.out.println("corect");
        	JOptionPane.showMessageDialog(
                    this,
                    "Datum nije u redu!",
                    "Obavestenje",
                    JOptionPane.INFORMATION_MESSAGE
                );
                return;
        }
        
        model.setRowCount(0);
        
        v = Vozilo.createVozilo(tip);     
        v.setDatum_isteka_registracije(regIstek);

        var times = controller.getSlobTerm(date, v);
        
        if (times.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Nema slobodnih termina za izabrani datum.",
                "Obavestenje",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        for (LocalTime t : times) {
            model.addRow(new Object[]{date, t});
        }
    }
    
    private LocalDate convertToLocalDate(java.util.Date date) {
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }
    
    private void resetPanel() {
        model.setRowCount(0);
        vehicleCombo.setSelectedIndex(0);
        dateSpinner.setValue(initDate);
        table.clearSelection();
    }
}