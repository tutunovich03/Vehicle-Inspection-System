package view;

import javax.swing.*;

import controller.GostController;
import model.Korisnik;
import model.KorisnikTip;

import java.awt.*;

public class ClientLoginPanel extends JPanel {

    public ClientLoginPanel(
            MainFrame frame,
            GostController controller
    ) {

        setLayout(
                new GridLayout(4, 2, 10, 10)
        );

        // =====================================
        // FIELDS
        // =====================================

        JTextField emailField =
                new JTextField();

        JPasswordField passField =
                new JPasswordField();

        // =====================================
        // BUTTONS
        // =====================================

        JButton loginBtn =
                new JButton("Login");

        JButton backBtn =
                new JButton("Nazad");

        // =====================================
        // ADD
        // =====================================

        add(new JLabel("Email:"));
        add(emailField);

        add(new JLabel("Password:"));
        add(passField);

        add(loginBtn);
        add(backBtn);

        // =====================================
        // LOGIN ACTION
        // =====================================

        loginBtn.addActionListener(e -> {

            try {

                String email =
                        emailField.getText();

                String pass =
                        new String(
                                passField.getPassword()
                        );

                Korisnik k =
                        controller.getNalog(
                                KorisnikTip.VLASNIK,
                                email,
                                pass
                        );

                // =============================
                // FAILED LOGIN
                // =============================

                if(k == null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Pogresan email ili sifra."
                    );

                    return;
                }

                // =============================
                // SUCCESS
                // =============================

                JOptionPane.showMessageDialog(
                        this,
                        "Uspesna prijava."
                );

                frame.openClientPanel(k);
            }
            catch(Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Greska pri prijavi."
                );

                ex.printStackTrace();
            }
        });

        // =====================================
        // BACK
        // =====================================

        backBtn.addActionListener(e -> {

            frame.showPanel("ROLE");
        });
    }
}