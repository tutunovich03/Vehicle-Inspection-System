package program;

import javax.swing.*;

import model.Sekretarica;
import view.SekretaricaPanel;

public class MainAppSekretarica {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame =
                    new JFrame("Sekretarica Panel");

            frame.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE
            );

            frame.setSize(1000, 700);

            // =====================================
            // PANEL
            // =====================================
            
            Sekretarica sekr = new Sekretarica();
            sekr.setId(18);
            
            SekretaricaPanel panel =
                    new SekretaricaPanel(sekr);

            frame.add(panel);

            // =====================================
            // FRAME SETTINGS
            // =====================================

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        });
    }
}