package view;

import model.Termin;
import model.Vlasnik;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.SekretaricaController;

import java.awt.*;
import java.util.ArrayList;

public class CancelAppointmentPanel extends JPanel {

    private JTable table;

    private DefaultTableModel tableModel;

    private ArrayList<Termin> currentTermini =
            new ArrayList<>();

    public CancelAppointmentPanel(
            SekretaricaPanel parent,
            SekretaricaController controller
    ) {

        setLayout(new BorderLayout());

        // =================================================
        // TITLE
        // =================================================

        JLabel title = new JLabel(
                "Otkazivanje termina",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(title, BorderLayout.NORTH);

        // =================================================
        // INPUT PANEL
        // =================================================

        JPanel inputPanel = new JPanel();

        inputPanel.setLayout(
                new GridLayout(3, 2, 10, 10)
        );

        JTextField imeField =
                new JTextField();

        JTextField prezimeField =
                new JTextField();

        JTextField telefonField =
                new JTextField();

        inputPanel.add(new JLabel("Ime:"));
        inputPanel.add(imeField);

        inputPanel.add(new JLabel("Prezime:"));
        inputPanel.add(prezimeField);

        inputPanel.add(new JLabel("Broj telefona:"));
        inputPanel.add(telefonField);

        add(inputPanel, BorderLayout.WEST);

        // =================================================
        // TABLE
        // =================================================

        tableModel = new DefaultTableModel(
                new String[] {
                        "Datum",
                        "Vreme pocetka",
                        "Tip vozila"
                },
                0
        );

        table = new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // =================================================
        // BUTTONS
        // =================================================

        JPanel buttonPanel = new JPanel();

        JButton searchBtn =
                new JButton("Pronadji termine");

        JButton deleteBtn =
                new JButton("Obrisi termin");

        JButton backBtn =
                new JButton("Nazad");

        buttonPanel.add(searchBtn);

        buttonPanel.add(deleteBtn);

        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // =================================================
        // SEARCH ACTION
        // =================================================

        searchBtn.addActionListener(e -> {

            try {

                // =====================================
                // VALIDATION
                // =====================================

                if(imeField.getText().trim().isEmpty()
                        || prezimeField.getText().trim().isEmpty()
                        || telefonField.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Popunite sva polja."
                    );

                    return;
                }

                // =====================================
                // CREATE OWNER
                // =====================================

                Vlasnik vlasnik =
                        new Vlasnik();

                vlasnik.setIme(
                        imeField.getText()
                );

                vlasnik.setPrezime(
                        prezimeField.getText()
                );

                vlasnik.setBroj_telefona(
                        telefonField.getText()
                );

                // =====================================
                // GET TERMINI
                // =====================================

                currentTermini =
                        controller.getTerminiVlasnika(
                                vlasnik
                        );

                // =====================================
                // CLEAR TABLE
                // =====================================

                tableModel.setRowCount(0);

                // =====================================
                // EMPTY
                // =====================================

                if(currentTermini == null
                        || currentTermini.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Nema zakazanih termina."
                    );

                    return;
                }

                // =====================================
                // LOAD TABLE
                // =====================================

                for(Termin t : currentTermini) {

                    tableModel.addRow(
                            new Object[] {

                                    t.getDatum_Termina(),

                                    t.getVreme_pocetka(),

                                    t.getVozilo()
                                     .getTip()
                            }
                    );
                }

            }
            catch(Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Greska pri pretrazi."
                );

                ex.printStackTrace();
            }
        });

        // =================================================
        // DELETE ACTION
        // =================================================

        deleteBtn.addActionListener(e -> {

            int row =
                    table.getSelectedRow();

            if(row == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Izaberite termin."
                );

                return;
            }

            try {

                Termin t =
                        currentTermini.get(row);

                controller.deleteTermin(t);

                currentTermini.remove(row);

                tableModel.removeRow(row);

                JOptionPane.showMessageDialog(
                        this,
                        "Termin uspesno obrisan."
                );

            }
            catch(Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Greska pri brisanju."
                );

                ex.printStackTrace();
            }
        });

        // =================================================
        // BACK
        // =================================================

        backBtn.addActionListener(e -> {

            tableModel.setRowCount(0);

            currentTermini.clear();

            imeField.setText("");

            prezimeField.setText("");

            telefonField.setText("");

            parent.showPanel("MAIN");
        });
    }
}