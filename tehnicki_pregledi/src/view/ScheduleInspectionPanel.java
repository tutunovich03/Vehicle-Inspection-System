package view;

import model.Vlasnik;
import model.VlasnikTip;
import model.Vozilo;
import model.VoziloTip;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.SekretaricaController;
import controller.VlasnikController;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ScheduleInspectionPanel extends JPanel {
	
	private VlasnikController vlContr = new VlasnikController();
	private SekretaricaController sekretaricaController = new SekretaricaController();

    private JTable slotsTable;

    private DefaultTableModel tableModel;

    public ScheduleInspectionPanel(
            SekretaricaPanel parent
    ) {

        setLayout(new BorderLayout());

        // ==================================================
        // TITLE
        // ==================================================

        JLabel title = new JLabel(
                "Zakazivanje pregleda",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(title, BorderLayout.NORTH);

        // ==================================================
        // FORM PANEL
        // ==================================================

        JPanel formPanel = new JPanel();

        formPanel.setLayout(
                new GridLayout(8, 2, 10, 10)
        );

        // --------------------------------------------------
        // VLASNIK
        // --------------------------------------------------

        JTextField imeField =
                new JTextField();

        JTextField prezimeField =
                new JTextField();

        JTextField telefonField =
                new JTextField();

        JComboBox<VlasnikTip> vlasnikTipCombo =
                new JComboBox<>(
                        VlasnikTip.values()
                );

        // --------------------------------------------------
        // VOZILO
        // --------------------------------------------------

        JTextField regDateField =
                new JTextField();

        JComboBox<VoziloTip> voziloTipCombo =
                new JComboBox<>(
                        VoziloTip.values()
                );

        // --------------------------------------------------

        formPanel.add(new JLabel("Ime:"));
        formPanel.add(imeField);

        formPanel.add(new JLabel("Prezime:"));
        formPanel.add(prezimeField);

        formPanel.add(new JLabel("Broj telefona:"));
        formPanel.add(telefonField);

        formPanel.add(new JLabel("Tip vlasnika:"));
        formPanel.add(vlasnikTipCombo);

        formPanel.add(
                new JLabel(
                        "Datum isteka registracije:"
                )
        );

        formPanel.add(regDateField);

        formPanel.add(new JLabel("Tip vozila:"));
        formPanel.add(voziloTipCombo);

        add(formPanel, BorderLayout.WEST);

        // ==================================================
        // TABLE
        // ==================================================

        tableModel = new DefaultTableModel(
                new String[] {
                        "Datum",
                        "Vreme"
                },
                0
        );

        slotsTable = new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(slotsTable);

        add(scrollPane, BorderLayout.CENTER);

        // ==================================================
        // BUTTON PANEL
        // ==================================================

        JPanel buttonPanel = new JPanel();

        JButton loadSlotsBtn =
                new JButton("Ucitaj termine");

        JButton scheduleBtn =
                new JButton("Zakazi");

        JButton backBtn =
                new JButton("Nazad");

        buttonPanel.add(loadSlotsBtn);

        buttonPanel.add(scheduleBtn);

        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // ==================================================
        // ACTIONS
        // ==================================================

        backBtn.addActionListener(e -> {
            parent.showPanel("MAIN");
        });

        loadSlotsBtn.addActionListener(e -> {

            try {

                // =========================================
                // PARSE DATE
                // =========================================

                LocalDate regDate =
                        LocalDate.parse(
                                regDateField.getText()
                        );

                // =========================================
                // CREATE TEMP OWNER
                // =========================================

                Vlasnik vlasnik = new Vlasnik();

                vlasnik.setIme(
                        imeField.getText()
                );

                vlasnik.setPrezime(
                        prezimeField.getText()
                );

                vlasnik.setBroj_telefona(
                        telefonField.getText()
                );

                vlasnik.setTip(
                        (VlasnikTip)
                                vlasnikTipCombo
                                        .getSelectedItem()
                );

                // =========================================
                // CREATE TEMP VEHICLE
                // =========================================

                Vozilo vozilo = Vozilo.createVozilo((VoziloTip)
                        voziloTipCombo
                        .getSelectedItem());

                vozilo.setDatum_isteka_registracije(
                        regDate
                );

                vozilo.setVlasnik(vlasnik);

                // =========================================
                // GET DATE FOR INSPECTION
                // =========================================

                String datumString =
                        JOptionPane.showInputDialog(
                                this,
                                "Unesite datum pregleda (yyyy-MM-dd):"
                        );

                if(datumString == null) {
                    return;
                }

                LocalDate pregledDate =
                        LocalDate.parse(datumString);

                // =========================================
                // LOAD AVAILABLE SLOTS
                // =========================================

                ArrayList<LocalTime> slots =
                        vlContr.getSlobTerm(
                                pregledDate,
                                vozilo
                        );

                // =========================================
                // CLEAR TABLE
                // =========================================

                tableModel.setRowCount(0);

                // =========================================
                // EMPTY CASE
                // =========================================

                if(slots == null || slots.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Nema slobodnih termina."
                    );

                    return;
                }

                // =========================================
                // ADD ROWS
                // =========================================

                for(LocalTime time : slots) {

                    tableModel.addRow(
                            new Object[] {
                                    pregledDate,
                                    time
                            }
                    );
                }

            }
            catch(Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Neispravan unos."
                );

                ex.printStackTrace();
            }
            
            
        });
        
        scheduleBtn.addActionListener(e -> {

            try {

                // =========================================
                // VALIDATION
                // =========================================

                if(imeField.getText().trim().isEmpty()
                        || prezimeField.getText().trim().isEmpty()
                        || telefonField.getText().trim().isEmpty()
                        || regDateField.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Sva polja moraju biti popunjena."
                    );

                    return;
                }

                // =========================================
                // SELECTED ROW
                // =========================================

                int row =
                        slotsTable.getSelectedRow();

                if(row == -1) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Izaberite termin."
                    );

                    return;
                }

                // =========================================
                // GET TABLE VALUES
                // =========================================

                LocalDate datumTermina =
                        (LocalDate)
                                tableModel.getValueAt(
                                        row,
                                        0
                                );

                LocalTime vreme =
                        (LocalTime)
                                tableModel.getValueAt(
                                        row,
                                        1
                                );

                // =========================================
                // CREATE OWNER
                // =========================================

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

                vlasnik.setTip(
                        (VlasnikTip)
                                vlasnikTipCombo
                                        .getSelectedItem()
                );

                // =========================================
                // CREATE VEHICLE
                // =========================================

                Vozilo vozilo = Vozilo.createVozilo((VoziloTip)
                        voziloTipCombo
                        .getSelectedItem());

                vozilo.setDatum_isteka_registracije(
                        LocalDate.parse(
                                regDateField.getText()
                        )
                );

                vozilo.setVlasnik(vlasnik);

                // =========================================
                // ADD OWNER
                // =========================================

                sekretaricaController.addVlasnik(
                        vlasnik
                );

                // =========================================
                // SCHEDULE APPOINTMENT
                // =========================================

                vlContr.addTerminVozilo(
                        vreme,
                        datumTermina,
                        vozilo,
                        vlasnik
                );

                // =========================================
                // SUCCESS
                // =========================================

                JOptionPane.showMessageDialog(
                        this,
                        "Termin uspesno zakazan."
                );

                // =========================================
                // RESET FORM
                // =========================================

                imeField.setText("");

                prezimeField.setText("");

                telefonField.setText("");

                regDateField.setText("");

                tableModel.setRowCount(0);

            }
            catch(Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Greska pri zakazivanju."
                );

                ex.printStackTrace();
            }
        });

        // TODO:
        // scheduleBtn action
    }
}