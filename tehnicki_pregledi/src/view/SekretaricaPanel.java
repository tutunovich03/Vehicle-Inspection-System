package view;

import javax.swing.*;

import controller.SekretaricaController;
import model.Sekretarica;

import java.awt.*;

public class SekretaricaPanel extends JPanel {

    private CardLayout cardLayout;
    private SekretaricaController sekretaricaController = new SekretaricaController();

    public SekretaricaPanel(Sekretarica sekr) {

        cardLayout = new CardLayout();

        setLayout(cardLayout);

        // =========================================
        // PANELS
        // =========================================

        SekretaricaMainPanel mainPanel =
                new SekretaricaMainPanel(this);

        // kasnije:
        // SchedulePanel schedulePanel = ...
        // CancelPanel cancelPanel = ...
        // VehicleDataPanel vehicleDataPanel = ...

        // =========================================
        // ADD PANELS
        // =========================================

        add(mainPanel, "MAIN");

        // add(schedulePanel, "SCHEDULE");
        // add(cancelPanel, "CANCEL");
        // add(vehicleDataPanel, "VEHICLE_DATA");

        // =========================================
        // INITIAL PANEL
        // =========================================

        cardLayout.show(this, "MAIN");
        
        
        ScheduleInspectionPanel schedulePanel =
                new ScheduleInspectionPanel(this);

        add(schedulePanel, "SCHEDULE");
        
        
        
        CancelAppointmentPanel cancelPanel =
                new CancelAppointmentPanel(
                        this,
                        sekretaricaController
                );

        add(cancelPanel, "CANCEL");
    }

    public void showPanel(String name) {

        cardLayout.show(this, name);
    }
}