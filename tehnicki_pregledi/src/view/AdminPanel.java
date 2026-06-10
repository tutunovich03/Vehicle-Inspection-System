package view;

import javax.swing.*;

import controller.AdminController;
import model.Admin;

import java.awt.*;

public class AdminPanel extends JPanel {

    private CardLayout cardLayout;

    public AdminPanel(AdminController controller, Admin a) {

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // PANELS
        AdminMainPanel mainPanel = new AdminMainPanel(this);

        // later:
        // WorkerManagementPanel workersPanel =
        //         new WorkerManagementPanel(this, controller);

        // add panels
        add(mainPanel, "MAIN");

        // later:
        // add(workersPanel, "WORKERS");

        // show first panel
        cardLayout.show(this, "MAIN");
        
        WorkerManagementPanel workersPanel =
                new WorkerManagementPanel(this, controller);

        add(workersPanel, "WORKERS");
        
        WorkBreakRequestsPanel requestsPanel =
                new WorkBreakRequestsPanel(this, controller);

        add(requestsPanel, "REQUESTS");
        
        WorkTimePanel workTimePanel =
                new WorkTimePanel(this, controller);

        add(workTimePanel, "WORKTIME");
        
        SettingsPanel pausesPanel =
                new SettingsPanel(this, controller);

        add(pausesPanel, "OSTALA_PODESAVANJA");
    }

    public void showPanel(String name) {
        cardLayout.show(this, name);
    }
}