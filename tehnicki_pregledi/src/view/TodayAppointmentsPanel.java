package view;

import model.Tehnicar;
import model.Termin;
import model.TerminState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.TehnicarController;

import java.awt.*;
import java.util.ArrayList;

public class TodayAppointmentsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private ArrayList<Termin> currentAppointments = new ArrayList<>();

    private final Tehnicar loggedTehnicar;
    private final TehnicarController controller;

    public TodayAppointmentsPanel(
            TehnicarPanel parent,
            TehnicarController controller,
            Tehnicar loggedTehnicar
    ) {

        this.controller = controller;
        this.loggedTehnicar = loggedTehnicar;

        setLayout(new BorderLayout());

        // ==================================================
        // TITLE
        // ==================================================

        JLabel title = new JLabel(
                "Danasnji termini",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        add(title, BorderLayout.NORTH);

        // ==================================================
        // TABLE
        // ==================================================

        model = new DefaultTableModel(
                new String[]{
                        
                        "Vreme",
                        "Tip vozila",
                        "stanje"
                },
                0
        );

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ==================================================
        // BUTTONS
        // ==================================================

        JPanel buttons = new JPanel();

        JButton startBtn = new JButton("Zapocni");
        JButton pauseBtn = new JButton("Prekini");
        JButton noShowBtn = new JButton("Neodrzan");
        JButton okBtn = new JButton("Ispravan");
        JButton failBtn = new JButton("Neispravan");
        JButton refreshBtn = new JButton("Osvezi");
        JButton backBtn = new JButton("Nazad");

        buttons.add(startBtn);
        buttons.add(pauseBtn);
        buttons.add(noShowBtn);
        buttons.add(okBtn);
        buttons.add(failBtn);
        buttons.add(refreshBtn);
        buttons.add(backBtn);

        add(buttons, BorderLayout.SOUTH);

        // ==================================================
        // ACTIONS
        // ==================================================

        refreshBtn.addActionListener(e ->
                loadAppointments()
        );

        startBtn.addActionListener(e -> {
        	
        	Termin t = getSelected();
        	if (t == null) return;
        	if(t.getStanje() != TerminState.REZERVISAN && t.getStanje() != TerminState.PREKINUT) {
        		JOptionPane.showMessageDialog(
                        this,
                        "Termin je vec zapocet"
                );
        		return;
        	}
        	
            
            

            controller.setTermZapocet(t);
            loadAppointments();
        });

        pauseBtn.addActionListener(e -> {
            Termin t = getSelected();
            
            if (t == null) return;
            
            if(t.getStanje() != TerminState.U_TOKU) {
        		JOptionPane.showMessageDialog(
                        this,
                        "Termin nije u toku"
                );
        		return;
        	}
            
            

            controller.setTerminPrekinut(t);
            loadAppointments();
        });

        noShowBtn.addActionListener(e -> {
            Termin t = getSelected();
            
            if (t == null) return;
            
            if(t.getStanje() != TerminState.REZERVISAN) {
        		JOptionPane.showMessageDialog(
                        this,
                        "Termin je u toku"
                );
        		return;
        	}
            
            

            controller.setNeodrzanTermin(t);
            loadAppointments();
        });

        okBtn.addActionListener(e -> {
            Termin t = getSelected();
            
            if (t == null) return;
            
            if(t.getStanje() != TerminState.U_TOKU) {
        		JOptionPane.showMessageDialog(
                        this,
                        "Termin nije u toku"
                );
        		return;
        	}
            
            

            controller.setRezultat(t, true);
            loadAppointments();
        });

        failBtn.addActionListener(e -> {
            Termin t = getSelected();
            
            if (t == null) return;
            
            if(t.getStanje() != TerminState.U_TOKU) {
        		JOptionPane.showMessageDialog(
                        this,
                        "Termin nije u toku"
                );
        		return;
        	}
            
            

            controller.setRezultat(t, false);
            loadAppointments();
        });

        backBtn.addActionListener(e ->
                parent.showPanel("MAIN")
        );

        // initial load
        loadAppointments();
    }

    // ==================================================
    // LOAD DATA
    // ==================================================

    private void loadAppointments() {

        currentAppointments =
                controller.getTerminiDanas(loggedTehnicar);

        model.setRowCount(0);

        if (currentAppointments == null) return;

        for (Termin t : currentAppointments) {

            model.addRow(new Object[]{

                    
                    t.getVreme_pocetka(),
                    t.getVozilo().getTip(),
                    t.getStanje()
            });
        }
    }

    // ==================================================
    // GET SELECTED TERMIN
    // ==================================================

    private Termin getSelected() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Izaberite termin."
            );

            return null;
        }

        return currentAppointments.get(row);
    }
}