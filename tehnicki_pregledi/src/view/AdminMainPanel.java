package view;

import javax.swing.*;
import java.awt.*;

public class AdminMainPanel extends JPanel {

    public AdminMainPanel(AdminPanel parent) {

        setLayout(new BorderLayout());

        // TITLE
        JLabel title = new JLabel("ADMIN PANEL", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        add(title, BorderLayout.NORTH);

        // CENTER BUTTONS
        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton workersBtn = new JButton("Radnici");
        JButton requestsBtn = new JButton("Zahtevi za odsustvo");
        JButton workTimeBtn = new JButton("Radno vreme");
        JButton otherSettingsBtn = new JButton("Ostala podesavanja");

        centerPanel.add(workersBtn);
        centerPanel.add(requestsBtn);
        centerPanel.add(workTimeBtn);
        centerPanel.add(otherSettingsBtn);

        add(centerPanel, BorderLayout.CENTER);

        // ACTIONS

        workersBtn.addActionListener(e -> {
            parent.showPanel("WORKERS");
        });

        requestsBtn.addActionListener(e -> {
            parent.showPanel("REQUESTS");
        });

        workTimeBtn.addActionListener(e -> {
            parent.showPanel("WORKTIME");
        });

        otherSettingsBtn.addActionListener(e -> {
            parent.showPanel("OSTALA_PODESAVANJA");
        });
    }
}