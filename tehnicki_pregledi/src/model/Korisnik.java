package model;

import java.io.*;
import java.util.*;

import exceptions.UserCreateException;


/**
 * 
 */
public abstract class Korisnik {

    /**
     * Default constructor
     */
    public Korisnik() {
    }

    protected int id;
    protected String broj_telefona;
    protected String ime;
    protected String prezime;

    public Korisnik(int id, String ime, String prezime, String broj_telefona) throws UserCreateException {
		super();
		if(ime.matches(".*\\d.*") 
			|| prezime.matches(".*\\d.*")
			|| broj_telefona.matches(".*\\D.*")) throw new UserCreateException();
		
		this.broj_telefona = broj_telefona;
		this.ime = ime;
		this.prezime = prezime;
		this.id = id;
	}

	@Override
	public String toString() {
		return "Korisnik [id=" + id + ", broj_telefona=" + broj_telefona + ", ime=" + ime + ", prezime=" + prezime
				+ "]";
	}

	public String getBroj_telefona() {
		return broj_telefona;
	}

	public void setBroj_telefona(String broj_telefona) {
		this.broj_telefona = broj_telefona;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
     * 
     */
    public void podaci() {
        // TODO implement here
    }

}