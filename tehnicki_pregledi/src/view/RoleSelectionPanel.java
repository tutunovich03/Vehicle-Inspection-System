package view;

import javax.swing.*;
import java.awt.*;

public class RoleSelectionPanel extends JPanel {

    public RoleSelectionPanel(
            MainFrame frame
    ) {

        setLayout(new GridLayout(4, 1, 20, 20));

        JButton adminBtn =
                new JButton("Admin");

        JButton clientBtn =
                new JButton("Klijent");

        JButton techBtn =
                new JButton("Tehnicar");

        JButton secretaryBtn =
                new JButton("Sekretarica");

        add(adminBtn);
        add(clientBtn);
        add(techBtn);
        add(secretaryBtn);

        // =====================================
        // ACTIONS
        // =====================================

        adminBtn.addActionListener(e -> {
            frame.showPanel("ADMIN_LOGIN");
        });

        clientBtn.addActionListener(e -> {
            frame.showPanel("CLIENT_LOGIN");
        });

        techBtn.addActionListener(e -> {
            frame.showPanel("TECH_LOGIN");
        });

        secretaryBtn.addActionListener(e -> {
            frame.showPanel("SECRETARY_LOGIN");
        });
    }
}
