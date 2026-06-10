package view;

import javax.swing.*;

import controller.AdminController;

import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Iterator;

public class WorkTimePanel extends JPanel {

    public WorkTimePanel(AdminPanel parent,
                         AdminController controller) {

        setLayout(new BorderLayout());

        // TITLE

        JLabel title = new JLabel(
                "Radno vreme",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        add(title, BorderLayout.NORTH);

        // MAIN PANEL

        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(new GridLayout(2, 1));

        // ==================================================
        // WEEKLY WORK TIME
        // ==================================================

        JPanel weeklyPanel = new JPanel();

        weeklyPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Radno vreme dana u nedelji"
                )
        );

        JComboBox<DayOfWeek> dayCombo =
                new JComboBox<>(DayOfWeek.values());

        JComboBox<Integer> dayHourCombo_from = new JComboBox<>();
        for(int i = 0; i<24; i++) {
        	dayHourCombo_from.addItem(i);
        }

        JComboBox<Integer> dayHourCombo_to = new JComboBox<>();
        for(int i = 0; i<24; i++) {
        	dayHourCombo_to.addItem(i);
        }

        JButton setWeeklyBtn =
                new JButton("Postavi");

        weeklyPanel.add(new JLabel("Dan:"));

        weeklyPanel.add(dayCombo);

        weeklyPanel.add(new JLabel("Od (HH):"));

        weeklyPanel.add(dayHourCombo_from);

        weeklyPanel.add(new JLabel("Do (HH):"));

        weeklyPanel.add(dayHourCombo_to);

        weeklyPanel.add(setWeeklyBtn);

        // ==================================================
        // SPECIFIC DATE WORK TIME
        // ==================================================

        JPanel specificDatePanel = new JPanel();

        specificDatePanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Radno vreme specificnog datuma"
                )
        );

        JComboBox<Integer> dateMonthCombo = new JComboBox<Integer>();
        for(int i = 1; i<=12; i++) {
        	dateMonthCombo.addItem(i);
        }
        dateMonthCombo.setSelectedIndex(0);
        
        JComboBox<Integer> dateDayCombo = new JComboBox<Integer>();
        for(int i = 1; i <= YearMonth.now()
                .withMonth(Integer.parseInt(dateMonthCombo.getSelectedItem().toString()))
                .lengthOfMonth(); i++) {
        	dateDayCombo.addItem(i);
        }

        JTextField fromField2 =
                new JTextField(5);

        JTextField toField2 =
                new JTextField(5);

        JCheckBox permanentCheck =
                new JCheckBox("Stalno");

        JButton setDateBtn =
                new JButton("Postavi");

        specificDatePanel.add(
                new JLabel("Mesec:")
        );

        specificDatePanel.add(dateMonthCombo);
        
        specificDatePanel.add(
                new JLabel("Dan:")
        );
        
        specificDatePanel.add(dateDayCombo);

        specificDatePanel.add(
                new JLabel("Od:")
        );

        specificDatePanel.add(fromField2);

        specificDatePanel.add(
                new JLabel("Do:")
        );

        specificDatePanel.add(toField2);

        specificDatePanel.add(permanentCheck);

        specificDatePanel.add(setDateBtn);

        // ==================================================

        centerPanel.add(weeklyPanel);

        centerPanel.add(specificDatePanel);

        add(centerPanel, BorderLayout.CENTER);

        // ==================================================
        // SOUTH PANEL
        // ==================================================

        JPanel southPanel = new JPanel();

        JButton backBtn = new JButton("Nazad");

        southPanel.add(backBtn);

        add(southPanel, BorderLayout.SOUTH);

        // ==================================================
        // ACTIONS
        // ==================================================

        setWeeklyBtn.addActionListener(e -> {

            try {

                DayOfWeek day =
                        (DayOfWeek)
                                dayCombo.getSelectedItem();

                LocalTime from =
                        LocalTime.of(Integer.parseInt(dayHourCombo_from.getSelectedItem().toString()), 0);

                LocalTime to =
                		LocalTime.of(Integer.parseInt(dayHourCombo_to.getSelectedItem().toString()), 0);

                controller.setRadnoVremeDana(
                        day,
                        from,
                        to
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Radno vreme postavljeno."
                );
            }
            catch(Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Neispravan unos."
                );
            }
        });

        // ==================================================

        setDateBtn.addActionListener(e -> {

            try {

                String datum =
                        dateMonthCombo.getSelectedItem().toString()+"-"+dateDayCombo.getSelectedItem();

                LocalTime from =
                        LocalTime.parse(
                                fromField2.getText()
                        );

                LocalTime to =
                        LocalTime.parse(
                                toField2.getText()
                        );

                boolean stalno =
                        permanentCheck.isSelected();

                controller.setRadnoVremeDatuma(
                        datum,
                        from,
                        to,
                        stalno
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Radno vreme postavljeno."
                );
            }
            catch(Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Neispravan unos."
                );
            }
        });

        // ==================================================

        backBtn.addActionListener(e -> {
            parent.showPanel("MAIN");
        });
    }
}
