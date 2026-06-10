package view;

import javax.swing.*;

import controller.GostController;
import model.Korisnik;
import model.KorisnikTip;
import model.Tehnicar;

import java.awt.*;

public class TechnicianLoginPanel extends JPanel {

    public TechnicianLoginPanel(
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
                                KorisnikTip.ZAPOSLENI,
                                email,
                                pass
                        );

                // =================================
                // LOGIN FAILED
                // =================================

                if(k == null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Pogresan email ili sifra."
                    );

                    return;
                }

                // =================================
                // CHECK TYPE
                // =================================

                if(!(k instanceof Tehnicar)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Nalog nije tehnicar."
                    );

                    return;
                }

                // =================================
                // SUCCESS
                // =================================

                JOptionPane.showMessageDialog(
                        this,
                        "Uspesna prijava."
                );

                frame.openTechnicianPanel(k);
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