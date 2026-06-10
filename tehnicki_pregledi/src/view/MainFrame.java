package view;

import javax.swing.*;

import controller.AdminController;
import controller.GostController;
import controller.TehnicarController;
import controller.VlasnikController;
import controller.ZaposleniController;
import model.Admin;
import model.Korisnik;
import model.Sekretarica;
import model.Tehnicar;
import model.Vlasnik;

import java.awt.*;

public class MainFrame extends JFrame {
	
	private AdminController adminContr = new AdminController();
	private GostController gController = new GostController();
	private VlasnikController vController = new VlasnikController();
	private ZaposleniController zController = new ZaposleniController();
	private TehnicarController tController = new TehnicarController();

    private CardLayout cardLayout;

    private JPanel mainPanel;

    public MainFrame() {

        setTitle("Tehnicki pregled");

        setSize(1200, 800);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        cardLayout = new CardLayout();

        mainPanel = new JPanel(cardLayout);

        // =====================================
        // PANELS
        // =====================================

        RoleSelectionPanel rolePanel =
                new RoleSelectionPanel(this);

        AdminLoginPanel adminLogin =
                new AdminLoginPanel(this, gController);

        ClientLoginPanel clientLogin =
                new ClientLoginPanel(this, gController);

        TechnicianLoginPanel techLogin =
                new TechnicianLoginPanel(this, gController);

        SecretaryLoginPanel secretaryLogin =
                new SecretaryLoginPanel(this, gController);

        

        // =====================================
        // ADD
        // =====================================

        mainPanel.add(rolePanel, "ROLE");

        mainPanel.add(adminLogin, "ADMIN_LOGIN");
        mainPanel.add(clientLogin, "CLIENT_LOGIN");
        mainPanel.add(techLogin, "TECH_LOGIN");
        mainPanel.add(secretaryLogin, "SECRETARY_LOGIN");

        

        add(mainPanel);

        showPanel("ROLE");
    }

    public void showPanel(String name) {

        cardLayout.show(mainPanel, name);
    }
    
    public void openAdminPanel(
            Korisnik k
    ) {

        AdminPanel panel =
                new AdminPanel(adminContr, (Admin)k);

        mainPanel.add(panel, "ADMIN");

        cardLayout.show(mainPanel, "ADMIN");
    }
    
    public void openClientPanel(
            Korisnik k
    ) {
    	
        ClientPanel panel =
                new ClientPanel(vController, (Vlasnik)k);

        mainPanel.add(panel, "CLIENT");

        cardLayout.show(mainPanel, "CLIENT");
    }
    
    public void openTechnicianPanel(
            Korisnik k
    ) {

        TehnicarPanel panel =
                new TehnicarPanel(tController,zController, (Tehnicar)k);

        mainPanel.add(panel, "TECH");

        cardLayout.show(mainPanel, "TECH");
    }
    
    public void openSecretaryPanel(
            Korisnik k
    ) {

        SekretaricaPanel panel =
                new SekretaricaPanel((Sekretarica)k);

        mainPanel.add(panel, "SECRETARY");

        cardLayout.show(mainPanel, "SECRETARY");
    }
    
    
}