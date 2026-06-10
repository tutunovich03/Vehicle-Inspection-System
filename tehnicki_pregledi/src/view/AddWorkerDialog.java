package view;

import exceptions.UserCreateException;
import model.Tehnicar;

import javax.swing.*;

import controller.AdminController;

import java.awt.*;

public class AddWorkerDialog extends JDialog {

    public AddWorkerDialog(AdminController controller) {

        setTitle("Dodavanje radnika");

        setSize(400, 500);

        setLocationRelativeTo(null);

        setModal(true);

        setLayout(new GridLayout(10, 2, 10, 10));

        // FIELDS

        JTextField idField = new JTextField();

        JTextField imeField = new JTextField();

        JTextField prezimeField = new JTextField();

        JTextField telefonField = new JTextField();

        JTextField plataField = new JTextField();

        JTextField godisnjiField = new JTextField();

        JTextField preostaliField = new JTextField();

        JTextField grupaField = new JTextField();

        JTextField pauzaField = new JTextField();

        // ADD COMPONENTS

        add(new JLabel("ID:"));
        add(idField);

        add(new JLabel("Ime:"));
        add(imeField);

        add(new JLabel("Prezime:"));
        add(prezimeField);

        add(new JLabel("Telefon:"));
        add(telefonField);

        add(new JLabel("Plata:"));
        add(plataField);

        add(new JLabel("Broj dana godisnjeg:"));
        add(godisnjiField);

        add(new JLabel("Preostali dani:"));
        add(preostaliField);

        add(new JLabel("Grupa:"));
        add(grupaField);

        add(new JLabel("Red pauze:"));
        add(pauzaField);

        JButton addBtn = new JButton("Dodaj");

        JButton cancelBtn = new JButton("Odustani");

        add(addBtn);
        add(cancelBtn);

        // ACTIONS

        cancelBtn.addActionListener(e -> {
            dispose();
        });

        addBtn.addActionListener(e -> {

            try {

                int id =
                        Integer.parseInt(idField.getText());

                String ime =
                        imeField.getText();

                String prezime =
                        prezimeField.getText();

                String telefon =
                        telefonField.getText();

                int plata =
                        Integer.parseInt(
                                plataField.getText()
                        );

                int godisnji =
                        Integer.parseInt(
                                godisnjiField.getText()
                        );

                int preostali =
                        Integer.parseInt(
                                preostaliField.getText()
                        );

                int grupa =
                        Integer.parseInt(
                                grupaField.getText()
                        );

                int redPauze =
                        Integer.parseInt(
                                pauzaField.getText()
                        );

                Tehnicar t = new Tehnicar(
                        id,
                        ime,
                        prezime,
                        telefon,
                        plata,
                        godisnji,
                        preostali,
                        grupa,
                        redPauze
                );

                controller.addZaposleni(t);

                JOptionPane.showMessageDialog(
                        this,
                        "Radnik dodat."
                );

                dispose();

            }
            catch(NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Neispravan unos."
                );
            }
            catch(UserCreateException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );
            }
        });
    }
}
