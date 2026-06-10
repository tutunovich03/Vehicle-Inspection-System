package model;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Nalog {

    /**
     * Default constructor
     */
    public Nalog() {
    }

    /**
     * 
     */
    private String mail;

    /**
     * 
     */
    private String lozinka;

    /**
     * 
     */
    private Korisnik korisnik;


    public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getLozinka() {
		return lozinka;
	}


	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}


	public Korisnik getKorisnik() {
		return korisnik;
	}


	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}


	/**
     * 
     */
    public void izbrisiNalog() {
        // TODO implement here
    }

}