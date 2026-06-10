package program;

import javax.swing.*;

import controller.AdminController;
import model.Admin;
import model.Vlasnik;
import view.AdminPanel;

public class MainAppAdmin {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Tehnicki pregled");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(900, 700);

        AdminController controller = new AdminController();
        
        Admin admin = new Admin();
        admin.setId(1);

        AdminPanel adminPanel = new AdminPanel(controller, admin);

        frame.add(adminPanel);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}