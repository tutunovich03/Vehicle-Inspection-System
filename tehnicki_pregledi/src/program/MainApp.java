package program;

import javax.swing.*;

import view.MainFrame;

public class MainApp {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            MainFrame frame =
                    new MainFrame();

            frame.setVisible(true);
        });
    }
}
