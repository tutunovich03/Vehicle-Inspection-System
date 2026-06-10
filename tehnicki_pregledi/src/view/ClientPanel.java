package view;

import javax.swing.*;

import controller.VlasnikController;
import model.Vlasnik;
import model.Vozilo;

import java.awt.*;
import java.util.ArrayList;

public class ClientPanel extends JPanel {

    private CardLayout cardLayout = new CardLayout();

    public ClientPanel(VlasnikController controller, Vlasnik client) {
        setLayout(cardLayout);

        MainMenuPanel main = new MainMenuPanel(this);
        AvailableSlotsPanel slots = new AvailableSlotsPanel(this, controller, client);
        ScheduledAppointmentsPanel scheduled = new ScheduledAppointmentsPanel(this, controller, client);
        HistoryPanel history = new HistoryPanel(this, controller, client);

        add(main, "MAIN");
        add(slots, "SLOTS");
        add(scheduled, "SCHEDULED");
        add(history, "HISTORY");
    }

    public void showPanel(String name) {
        cardLayout.show(this, name);
    }
}
