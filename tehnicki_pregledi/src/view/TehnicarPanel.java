package view;

import model.Tehnicar;

import javax.swing.*;

import controller.TehnicarController;
import controller.ZaposleniController;

import java.awt.*;

public class TehnicarPanel extends JPanel {

    private CardLayout cardLayout;

    public TehnicarPanel(
            TehnicarController tehnicarController,
            ZaposleniController zaposleniController,
            Tehnicar loggedTehnicar
    ) {

        cardLayout = new CardLayout();

        setLayout(cardLayout);

        // PANELS

        TehnicarMainPanel mainPanel =
                new TehnicarMainPanel(this);

        TodayAppointmentsPanel appointmentsPanel =
                new TodayAppointmentsPanel(
                        this,
                        tehnicarController,
                        loggedTehnicar
                );
        
        BreakRequestPanel brPanel = 
        		new BreakRequestPanel(this, 
        				zaposleniController, 
        				loggedTehnicar);

        // ADD PANELS
        
        add(brPanel, "REQUESTS");

        add(mainPanel, "MAIN");

        add(appointmentsPanel, "TODAY");

        // SHOW MAIN

        cardLayout.show(this, "MAIN");
    }

    public void showPanel(String name) {
        cardLayout.show(this, name);
    }
}
