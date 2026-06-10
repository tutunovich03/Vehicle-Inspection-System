package program;

import javax.swing.*;

import controller.TehnicarController;
import controller.ZaposleniController;
import model.Tehnicar;
import view.TehnicarPanel;

public class MainAppTehnicar {

    public static void main(String[] args) {

        JFrame frame =
                new JFrame("Tehnicki pregled");

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setSize(1000, 700);

        TehnicarController tehnicarController =
                new TehnicarController();

        ZaposleniController zaposleniController =
                new ZaposleniController();

        Tehnicar loggedTehnicar =
                new Tehnicar();
        
        loggedTehnicar.setId(4);
        loggedTehnicar.setBroj_dana_godisnjeg(15);

        TehnicarPanel panel =
                new TehnicarPanel(
                        tehnicarController,
                        zaposleniController,
                        loggedTehnicar
                );

        frame.add(panel);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}