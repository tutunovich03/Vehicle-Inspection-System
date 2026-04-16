package view;

import model.Vozilo;
import model.Nalog;
import model.Termin;
import model.Vlasnik;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * 
 */
public class VlasnikGUI extends GUI {

    /**
     * Default constructor
     */
    public VlasnikGUI() {
    }

    /**
     * @param vreme 
     * @param date 
     * @param v
     */
    public void btnZakaziTermin(LocalTime vreme, LocalDate date, Vozilo v) {
        // TODO implement here
    }

    /**
     * @param datum 
     * @param v
     */
    public void btnPrikazSlobTerm(LocalDate datum, Vozilo v) {
        // TODO implement here
    }

    /**
     * @param nalog
     */
    public void btnPrikaziZakazane(Nalog nalog) {
        // TODO implement here
    }

    /**
     * @param t
     */
    public void btnOtkaziTermin(Termin t) {
        // TODO implement here
    }

    /**
     * @param v
     */
    public void btnIstorijaPregleda(Vlasnik v) {
        // TODO implement here
    }

    /**
     * @param t
     */
    public void btnPreuzmiPotvrdu(Termin t) {
        // TODO implement here
    }

}