package model;

import java.io.*;
import java.time.LocalTime;
import java.util.*;

import exceptions.UserCreateException;

/**
 * 
 */
public class Tehnicar extends Zaposleni {

    
    private boolean trenutno_zauzet = false;

    

	public Tehnicar() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Tehnicar(int id, String ime, String prezime, String brTel, int plata, int broj_dana_godisnjeg,
			int preostali_dani_godisnjeg, int grupa, int vreme_za_pauzu) throws UserCreateException {
		super(id, ime, prezime, brTel, plata, broj_dana_godisnjeg, preostali_dani_godisnjeg, grupa, vreme_za_pauzu);
	}



	public boolean isTrenutno_zauzet() {
		return trenutno_zauzet;
	}



	public void setTrenutno_zauzet(boolean trenutno_zauzet) {
		this.trenutno_zauzet = trenutno_zauzet;
	}


	
	
    

}