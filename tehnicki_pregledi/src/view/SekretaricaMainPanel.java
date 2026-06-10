package view;

import javax.swing.*;
import java.awt.*;

public class SekretaricaMainPanel extends JPanel {

    public SekretaricaMainPanel(
            SekretaricaPanel parent
    ) {

        setLayout(new BorderLayout());

        // =========================================
        // TITLE
        // =========================================

        JLabel title = new JLabel(
                "SEKRETARICA PANEL",
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
                new GridLayout(3, 1, 15, 15)
        );

        JButton scheduleBtn =
                new JButton("Zakazivanje pregleda");

        JButton cancelBtn =
                new JButton("Otkazivanje pregleda");

        JButton vehicleDataBtn =
                new JButton("Popunjavanje podataka vozila");

        centerPanel.add(scheduleBtn);

        centerPanel.add(cancelBtn);

        centerPanel.add(vehicleDataBtn);

        add(centerPanel, BorderLayout.CENTER);

        // =========================================
        // ACTIONS
        // =========================================

        scheduleBtn.addActionListener(e -> {
            parent.showPanel("SCHEDULE");
        });

        cancelBtn.addActionListener(e -> {
            parent.showPanel("CANCEL");
        });

        vehicleDataBtn.addActionListener(e -> {
            parent.showPanel("VEHICLE_DATA");
        });
    }
}