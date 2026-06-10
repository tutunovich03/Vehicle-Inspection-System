package view;

import javax.swing.*;
import java.awt.*;

public class TehnicarMainPanel extends JPanel {

    public TehnicarMainPanel(TehnicarPanel parent) {

        setLayout(new BorderLayout());

        // =========================================
        // TITLE
        // =========================================

        JLabel title = new JLabel(
                "TEHNICAR PANEL",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(title, BorderLayout.NORTH);

        // =========================================
        // CENTER BUTTONS
        // =========================================

        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(
                new GridLayout(2, 1, 15, 15)
        );

        JButton todayAppointmentsBtn =
                new JButton("Danasnji termini");

        JButton requestsBtn =
                new JButton("Zahtevi za odsustvo");

        centerPanel.add(todayAppointmentsBtn);

        centerPanel.add(requestsBtn);

        add(centerPanel, BorderLayout.CENTER);

        // =========================================
        // ACTIONS
        // =========================================

        todayAppointmentsBtn.addActionListener(e -> {
            parent.showPanel("TODAY");
        });

        requestsBtn.addActionListener(e -> {
            parent.showPanel("REQUESTS");
        });
        
        
    }
}
