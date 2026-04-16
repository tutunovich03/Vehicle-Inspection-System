package view;

import model.Zaposleni;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public abstract class ZaposleniGUI {

    /**
     * Default constructor
     */
    public ZaposleniGUI() {
    }

    /**
     * @param z 
     * @param filePath
     */
    public void btnZatraziBolovanje(Zaposleni z, String filePath) {
        // TODO implement here
    }

    /**
     * @param z 
     * @param od 
     * @param do
     */
    public void btnZatraziGodisnji(Zaposleni z, LocalDate[] dateSpan) {
        // TODO implement here
    }

}