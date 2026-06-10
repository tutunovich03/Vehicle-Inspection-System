package model;

import java.io.*;
import java.time.LocalTime;
import java.util.*;

import exceptions.UserCreateException;

/**
 * 
 */
public class Sekretarica extends Zaposleni {

    /**
     * Default constructor
     * @throws UserCreateException 
     */

	public Sekretarica() {
		super();
	}
	
    public Sekretarica(int id, String ime, String prezime, String brTel, int plata, int broj_dana_godisnjeg,
			int preostali_dani_godisnjeg, int grupa, int vreme_za_pauzu) throws UserCreateException {
		super(id, ime, prezime, brTel, plata, broj_dana_godisnjeg, preostali_dani_godisnjeg, grupa, vreme_za_pauzu);
	}


	/**
     * 
     */
    public void unesi_vlasnika() {
        // TODO implement here
    }

    /**
     * 
     */
    public void unesi_vozilo() {
        // TODO implement here
    }

    /**
     * 
     */
    public void getTermin() {
        // TODO implement here
    }

    /**
     * 
     */
    public void getVlasnik() {
        // TODO implement here
    }

}