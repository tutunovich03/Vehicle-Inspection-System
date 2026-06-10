package view;

import model.Tehnicar;
import model.Zaposleni;

import javax.swing.*;

import controller.AdminController;

import java.awt.*;
import java.util.ArrayList;

public class SettingsPanel extends JPanel {

    private JComboBox<String> workerCombo;

    public SettingsPanel(AdminPanel parent,
                              AdminController controller) {

        setLayout(new BorderLayout());

        // TITLE

        JLabel title = new JLabel(
                "Settings",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        add(title, BorderLayout.NORTH);

        // CENTER PANEL

        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(
                new GridLayout(2, 1, 10, 10)
        );

        // ==================================================
        // GLOBAL PAUSE DURATION
        // ==================================================

        JPanel durationPanel = new JPanel();

        durationPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "pauze"
                )
        );

        JTextField durationField =
                new JTextField(5);

        JButton setDurationBtn =
                new JButton("Postavi trajanje");

        durationPanel.add(
                new JLabel("Trajanje pauze (min):")
        );

        durationPanel.add(durationField);

        durationPanel.add(setDurationBtn);

        ////////
        
        JPanel shiftPanel = new JPanel();
        
        shiftPanel.setBorder(BorderFactory.createTitledBorder("smene"));
        
        JComboBox<Integer> shiftCombo = new JComboBox<>(new Integer[] {1, 2, 3});

        JButton setBtn = new JButton("Postavi broj smena");
        
        shiftPanel.add(new JLabel("Broj smena: "));
        shiftPanel.add(shiftCombo);
        shiftPanel.add(setBtn);
        
        ///////

        centerPanel.add(durationPanel);
        centerPanel.add(shiftPanel);

        add(centerPanel, BorderLayout.CENTER);

        // SOUTH PANEL

        JPanel southPanel = new JPanel();

        JButton backBtn =
                new JButton("Nazad");

        southPanel.add(backBtn);

        add(southPanel, BorderLayout.SOUTH);

        // ==================================================
        // ACTIONS
        // ==================================================

        setDurationBtn.addActionListener(e -> {

            try {

                int minutes =
                        Integer.parseInt(
                                durationField.getText()
                        );

                controller.setTrajanjePauze(
                        minutes
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Trajanje pauze postavljeno."
                );
            }
            catch(Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Neispravan unos."
                );
            }
        });
        
        setBtn.addActionListener(e -> {

            try {

                int brojSmena =
                        (Integer)
                                shiftCombo.getSelectedItem();

                controller.setBrojSmena(
                        brojSmena
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Broj smena uspesno postavljen."
                );
            }
            catch(Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Greska pri postavljanju smena."
                );
            }
        });

        

        // ==================================================

        backBtn.addActionListener(e -> {
            parent.showPanel("MAIN");
        });
    }
}
