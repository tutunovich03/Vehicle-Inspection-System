package view;

import java.awt.BorderLayout;
import java.time.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

import controller.VlasnikController;
import model.Termin;
import model.TerminState;
import model.Vlasnik;
import model.Vozilo;

class ScheduledAppointmentsPanel extends JPanel {

    private DefaultTableModel model;
    private JTable table;
    private ArrayList<Termin> current = new ArrayList<>();

    public ScheduledAppointmentsPanel(ClientPanel parent, VlasnikController controller, Vlasnik client) {

        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Date", "Time", "Vozilo"}, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton cancelBtn = new JButton("Cancel Appointment");
        JButton refreshBtn = new JButton("Refresh");
        JButton backBtn = new JButton("Back");

        bottom.add(cancelBtn);
        bottom.add(refreshBtn);
        bottom.add(backBtn);

        add(bottom, BorderLayout.SOUTH);

        // LOAD
        Runnable load = () -> {
            model.setRowCount(0);
            current = controller.getTermini(client, TerminState.REZERVISAN);

            for (Termin t : current) {
                model.addRow(new Object[]{
                        t.getDatum_Termina(),
                        t.getVreme_pocetka(),
                        t.getVozilo().getTip()
                });
            }
        };

        refreshBtn.addActionListener(e -> load.run());

        cancelBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;

            controller.removeTermin(current.get(row));
            load.run();
        });

        backBtn.addActionListener(e -> parent.showPanel("MAIN"));

        load.run();
    }
}
