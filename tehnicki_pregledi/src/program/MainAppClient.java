package program;

import javax.swing.*;

import controller.VlasnikController;
import model.Vlasnik;
import model.Vozilo;
import view.ClientPanel;

import java.util.ArrayList;

public class MainAppClient {

    public static void main(String[] args) {

        // Create frame
        JFrame frame = new JFrame("Tehnicki pregled");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Fake data for now (you will replace this)
        VlasnikController controller = new VlasnikController();
        Vlasnik client = new Vlasnik();
        client.setId(12);

        // Create main client panel
        ClientPanel clientPanel = new ClientPanel(controller, client);

        frame.add(clientPanel);

        frame.setLocationRelativeTo(null); // center
        frame.setVisible(true);
    }
}