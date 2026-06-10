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

class HistoryPanel extends JPanel {

    private DefaultTableModel model;

    public HistoryPanel(ClientPanel parent, VlasnikController controller, Vlasnik client) {

        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Date", "Time", "Vehicle", "Result"}, 0);
        JTable table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        add(backBtn, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> parent.showPanel("MAIN"));

        load(controller, client);
    }

    private void load(VlasnikController controller, Vlasnik client) {

        model.setRowCount(0);

        var zavrseni = controller.getTermini(client, TerminState.ZAVRSEN);

        for (Termin t : zavrseni) {
            model.addRow(new Object[]{
                    t.getDatum_Termina(),
                    t.getVreme_pocetka(),
                    t.getVozilo().getModel(),
                    t.getStanje()
            });
        }
    }
}