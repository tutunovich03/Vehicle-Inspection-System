package model;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * 
 */
public class Vlasnik extends Korisnik {

    /**
     * Default constructor
     */
    public Vlasnik() {
    }

    private VlasnikTip tip;

	/**
     * 
     */

    public VlasnikTip getTip() {
		return tip;
	}

	public void setTip(VlasnikTip tip) {
		this.tip = tip;
	}

	/**
     * @param vozilo 
     * @param date 
     * @param vreme
     */
    public void zakazi_termin(Vozilo vozilo, LocalDate date, LocalTime vreme) {
        // TODO implement here
    }

    /**
     * 
     */
    public void prikazi_istoriju_pregleda() {
        // TODO implement here
    }

}