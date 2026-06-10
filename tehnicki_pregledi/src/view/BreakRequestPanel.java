package view;

import model.Tehnicar;
import model.Zaposleni;

import javax.swing.*;

import controller.ZaposleniController;

import java.awt.*;
import java.time.LocalDate;

public class BreakRequestPanel extends JPanel {

    private final ZaposleniController controller;
    private final Tehnicar loggedTehnicar;

    public BreakRequestPanel(
            TehnicarPanel parent,
            ZaposleniController controller,
            Tehnicar loggedTehnicar
    ) {

        this.controller = controller;
        this.loggedTehnicar = loggedTehnicar;

        setLayout(new BorderLayout());

        // ==================================================
        // TITLE
        // ==================================================

        JLabel title = new JLabel(
                "Zahtev za odsustvo",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        add(title, BorderLayout.NORTH);

        // ==================================================
        // CENTER FORM
        // ==================================================

        JPanel form = new JPanel();

        form.setLayout(new GridLayout(6, 2, 10, 10));

        JTextField fromField = new JTextField();
        JTextField toField = new JTextField();

        JTextField filePathField = new JTextField();

        JButton browseBtn = new JButton("Browse");

        JButton vacationBtn =
                new JButton("Zahtev za godisnji");

        JButton sickBtn =
                new JButton("Zahtev za bolovanje");

        // --------------------------------------------------

        form.add(new JLabel("Datum od (yyyy-MM-dd):"));
        form.add(fromField);

        form.add(new JLabel("Datum do (yyyy-MM-dd):"));
        form.add(toField);

        form.add(new JLabel("Dokument (samo za bolovanje):"));
        form.add(filePathField);

        form.add(browseBtn);

        form.add(vacationBtn);
        form.add(sickBtn);

        add(form, BorderLayout.CENTER);

        // ==================================================
        // SOUTH
        // ==================================================

        JPanel south = new JPanel();

        JButton backBtn = new JButton("Nazad");

        south.add(backBtn);

        add(south, BorderLayout.SOUTH);

        // ==================================================
        // ACTIONS
        // ==================================================

        browseBtn.addActionListener(e -> {

            JFileChooser chooser = new JFileChooser();

            int result =
                    chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {

                filePathField.setText(
                        chooser.getSelectedFile()
                                .getAbsolutePath()
                );
            }
        });

        // --------------------------------------------------

        vacationBtn.addActionListener(e -> {

            try {

                LocalDate od =
                        LocalDate.parse(fromField.getText());

                LocalDate doo =
                        LocalDate.parse(toField.getText());

                controller.addGodisnjiZahtev(
                        loggedTehnicar,
                        od,
                        doo
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Zahtev za godisnji poslat."
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Neispravan unos datuma."
                );
            }
        });

        // --------------------------------------------------

        sickBtn.addActionListener(e -> {

            try {

                LocalDate od =
                        LocalDate.parse(fromField.getText());

                LocalDate doo =
                        LocalDate.parse(toField.getText());

                String filePath =
                        filePathField.getText();

                controller.addBolovanjeZahtev(
                        loggedTehnicar,
                        filePath,
                        od,
                        doo
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Zahtev za bolovanje poslat."
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Neispravan unos."
                );
            }
        });

        // --------------------------------------------------

        backBtn.addActionListener(e ->
                parent.showPanel("MAIN")
        );
    }
    }
