package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.GostController;
import model.Korisnik;
import model.KorisnikTip;
import model.Sekretarica;
import model.Tehnicar;

public class SecretaryLoginPanel extends JPanel {

    public SecretaryLoginPanel(
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

                if(!(k instanceof Sekretarica)) {

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

                frame.openSecretaryPanel(k);
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