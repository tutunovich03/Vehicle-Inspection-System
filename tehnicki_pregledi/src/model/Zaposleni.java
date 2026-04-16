package model;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import exceptions.UserCreateException;

/**
 * 
 */
public abstract class Zaposleni extends Korisnik {

    @Override
	public String toString() {
		return super.toString() + "Zaposleni [id: " + this.id + ", plata=" + plata + ", broj_dana_godisnjeg=" + broj_dana_godisnjeg
				+ ", preostali_dani_godisnjeg=" + preostali_dani_godisnjeg + ", grupa=" + grupa + ", red_pauze="
				+ red_pauze + ", vreme_pauze=" + vreme_pauze + ", aktivan=" + aktivan + "]";
	}

	/**
     * Default constructor
     */
	
	 public Zaposleni() {
			super();
			
	}

    /**
     * 
     */
    protected int plata;
	/**
     * 
     */
    protected int broj_dana_godisnjeg;

    /**
     * 
     */
    protected int preostali_dani_godisnjeg;

    /**
     * 
     */
    protected int grupa;

    /**
     * 
     */
    protected int red_pauze;


    protected LocalTime vreme_pauze = null;
    
    protected boolean aktivan = true;


	public LocalTime getVreme_pauze() {
		return vreme_pauze;
	}

	public void setVreme_pauze(LocalTime vreme_pauze) {
		this.vreme_pauze = vreme_pauze;
	}

	public Zaposleni(int id, String ime, String prezime, String brTel, int plata, int broj_dana_godisnjeg, int preostali_dani_godisnjeg, int grupa,
			int pauza) throws UserCreateException {
		super(id, ime, prezime, brTel);
		this.plata = plata;
		this.broj_dana_godisnjeg = broj_dana_godisnjeg;
		this.preostali_dani_godisnjeg = preostali_dani_godisnjeg;
		this.grupa = grupa;
		this.red_pauze = pauza;
	}

	public int getPlata() {
		return plata;
	}

	public void setPlata(int satnica) {
		this.plata = satnica;
	}

	public int getBroj_dana_godisnjeg() {
		return broj_dana_godisnjeg;
	}

	public void setBroj_dana_godisnjeg(int broj_dana_godisnjeg) {
		this.broj_dana_godisnjeg = broj_dana_godisnjeg;
	}

	public int getPreostali_dani_godisnjeg() {
		return preostali_dani_godisnjeg;
	}

	public void setPreostali_dani_godisnjeg(int preostali_dani_godisnjeg) {
		this.preostali_dani_godisnjeg = preostali_dani_godisnjeg;
	}

	public int getGrupa() {
		return grupa;
	}

	public void setGrupa(int smena) {
		this.grupa = smena;
	}

	public int getRedPauze() {
		return red_pauze;
	}

	public void setRedPauze(int red_pauze) {
		this.red_pauze = red_pauze;
	}

	/**
     * @param datum
     */
    public void getVremeZaPauzu(LocalDate datum) {
        // TODO implement here
    }

    /**
     * @param datum
     */
    public void getRadnoVreme(LocalDate datum) {
        // TODO implement here
    }

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}
    
    

}