package view;

import javax.swing.*;

import controller.VlasnikController;
import model.Vlasnik;
import model.Vozilo;

import java.awt.*;
import java.util.ArrayList;

class MainMenuPanel extends JPanel {

    public MainMenuPanel(ClientPanel parent) {
    	
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton scheduleBtn = new JButton("Schedule Appointment");
        JButton myBtn = new JButton("My Appointments");
        JButton historyBtn = new JButton("History");

        add(new JLabel("Client Dashboard", SwingConstants.CENTER));
        add(scheduleBtn);
        add(myBtn);
        add(historyBtn);

        scheduleBtn.addActionListener(e -> parent.showPanel("SLOTS"));
        myBtn.addActionListener(e -> parent.showPanel("SCHEDULED"));
        historyBtn.addActionListener(e -> parent.showPanel("HISTORY"));
    }
}