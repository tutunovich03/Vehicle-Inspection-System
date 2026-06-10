package view;

import model.Prekid_Rada;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.AdminController;

import java.awt.*;
import java.util.ArrayList;

public class WorkBreakRequestsPanel extends JPanel {

    private JTable table;

    private DefaultTableModel model;

    private ArrayList<Prekid_Rada> currentRequests =
            new ArrayList<>();

    public WorkBreakRequestsPanel(AdminPanel parent,
                                  AdminController controller) {

        setLayout(new BorderLayout());

        // TITLE

        JLabel title = new JLabel(
                "Zahtevi za odsustvo",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        add(title, BorderLayout.NORTH);

        // TABLE

        model = new DefaultTableModel(
                new String[] {
                        
                        "Zaposleni",
                        "Datum od",
                        "Datum do",
                        
                },
                0
        );

        table = new JTable(model);

        JScrollPane scrollPane =
                new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // BUTTONS

        JPanel buttonPanel = new JPanel();

        JButton acceptBtn = new JButton("Prihvati");

        JButton rejectBtn = new JButton("Odbij");

        JButton refreshBtn = new JButton("Osvezi");

        JButton backBtn = new JButton("Nazad");

        buttonPanel.add(acceptBtn);
        buttonPanel.add(rejectBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // ACTIONS

        refreshBtn.addActionListener(e -> {
            loadRequests(controller);
        });

        acceptBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if(row == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Izaberite zahtev."
                );

                return;
            }

            Prekid_Rada pr =
                    currentRequests.get(row);

            controller.confirmPrekidRada(
                    true,
                    pr
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Zahtev prihvacen."
            );

            loadRequests(controller);
        });

        rejectBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if(row == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Izaberite zahtev."
                );

                return;
            }

            Prekid_Rada pr =
                    currentRequests.get(row);

            controller.confirmPrekidRada(
                    false,
                    pr
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Zahtev odbijen."
            );

            loadRequests(controller);
        });

        backBtn.addActionListener(e -> {
            parent.showPanel("MAIN");
        });

        // INITIAL LOAD

        loadRequests(controller);
    }

    private void loadRequests(
            AdminController controller
    ) {

        currentRequests =
                controller.prikaziZahteveZaPrekidRada();

        model.setRowCount(0);

        for(Prekid_Rada pr : currentRequests) {

            String zaposleniIme =
                    pr.getZaposleni().getIme()
                    + " "
                    + pr.getZaposleni().getPrezime();

            model.addRow(new Object[] {

                    zaposleniIme,
                    pr.getDatum_od(),
                    pr.getDatum_do(),
                    
            });
        }
    }
}