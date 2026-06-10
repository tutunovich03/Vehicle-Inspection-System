package view;

import model.Tehnicar;
import model.Zaposleni;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.AdminController;

import java.awt.*;
import java.util.ArrayList;

public class WorkerManagementPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private ArrayList<Zaposleni> currentWorkers = new ArrayList<>();

    public WorkerManagementPanel(AdminPanel parent,
                                 AdminController controller) {

        setLayout(new BorderLayout());

        // TITLE
        JLabel title = new JLabel(
                "Upravljanje radnicima",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        add(title, BorderLayout.NORTH);

        // TABLE

        model = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Ime",
                        "Prezime",
                        "Plata",
                        "Grupa",
                        "Red pauze",
                        "Radno mesto"
                },
                0
        );

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // BUTTONS

        JPanel buttonPanel = new JPanel();

        JButton addBtn = new JButton("Dodaj");
        JButton removeBtn = new JButton("Obrisi");
        JButton refreshBtn = new JButton("Osvezi");
        JButton backBtn = new JButton("Nazad");
        JButton setGroupBtn = new JButton("Postavi grupu");
        JButton setPauseBtn = new JButton("Postavi pauzu");
        
        int groups_number = controller.getBrojSmena();
        
        Integer[] groups = new Integer[groups_number];
        
        for(int i = 0; i < groups_number; i++) {
        	groups[i] = i + 1;
        }

        JComboBox<Integer> combo =
                new JComboBox<>(groups);

        buttonPanel.add(addBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(setGroupBtn);
        buttonPanel.add(setPauseBtn);
        buttonPanel.add(backBtn);
        

        add(buttonPanel, BorderLayout.SOUTH);

        // ACTIONS

        refreshBtn.addActionListener(e -> {
            loadWorkers(controller);
        });
        
        setGroupBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if(row == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Izaberite radnika."
                );

                return;
            }

            Zaposleni z = currentWorkers.get(row);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    combo,
                    "Izaberite grupu",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if(result != JOptionPane.OK_OPTION) {
                return;
            }

            int grupa =
                    (Integer) combo.getSelectedItem();

            controller.setZaposleniGrupa(
                    z,
                    grupa
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Grupa uspesno postavljena."
            );

            loadWorkers(controller);
        });
        
        setPauseBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if(row == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Izaberite radnika."
                );

                return;
            }

            Zaposleni z = currentWorkers.get(row);
            
            int broj_u_smeni = 0;
            
            for (Zaposleni zap : currentWorkers) {
				if(zap.getGrupa() == z.getGrupa() && zap.getClass() == z.getClass()) {
					broj_u_smeni ++;
				}
			}
            
            Integer[] redovi = new Integer[broj_u_smeni];
            
            for(int i = 0; i < broj_u_smeni; i++) {
                redovi[i] = i + 1;
            }

            JComboBox<Integer> combo_pause =
                    new JComboBox<>(
                            redovi
                    );

            int result = JOptionPane.showConfirmDialog(
                    this,
                    combo_pause,
                    "Izaberite red pauze",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if(result != JOptionPane.OK_OPTION) {
                return;
            }

            int red =
                    (Integer) combo.getSelectedItem();

            controller.setRedPauze(
                    z,
                    red
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Red pauze uspesno postavljen."
            );

            loadWorkers(controller);
        });

        removeBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if(row == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Izaberite radnika."
                );

                return;
            }

            Zaposleni z = currentWorkers.get(row);

            controller.removeZaposleni(z);

            JOptionPane.showMessageDialog(
                    this,
                    "Radnik obrisan."
            );

            loadWorkers(controller);
        });

        addBtn.addActionListener(e -> {

            AddWorkerDialog dialog =
                    new AddWorkerDialog(controller);

            dialog.setVisible(true);

            loadWorkers(controller);
        });

        backBtn.addActionListener(e -> {
            parent.showPanel("MAIN");
        });

        // INITIAL LOAD

        loadWorkers(controller);
    }

    private void loadWorkers(AdminController controller) {

        currentWorkers = controller.getZaposleniList();

        model.setRowCount(0);

        for(Zaposleni z : currentWorkers) {
        	
        	String rm = "Sekretarica";
        	
        	if(z instanceof Tehnicar)
        		rm = "Tehnicar";

            model.addRow(new Object[] {

                    z.getId(),
                    z.getIme(),
                    z.getPrezime(),
                    z.getPlata(),
                    z.getGrupa(),
                    z.getRedPauze(),
                    rm,
            });
        }
    }
}