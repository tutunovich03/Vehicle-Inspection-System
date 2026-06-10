package model;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * 
 */
public class Termin {

    @Override
	public String toString() {
		return "Termin [stanje=" + stanje + ", tehnicar_id=" + tehnicar.id + ", vozilo_id=" + vozilo + ", vreme_pocetka="
				+ vreme_pocetka + ", datum_Termina=" + datum_Termina + "]";
	}

	/**
     * Default constructor
     */
    public Termin() {
    }

    /**
     * 
     */
    private TerminState stanje;

    /**
     * 
     */
    private Tehnicar tehnicar;

    /**
     * 
     */
    private Vozilo vozilo;

    /**
     * 
     */
    private LocalTime vreme_pocetka;

    /**
     * 
     */
    private LocalDate datum_Termina;
    
    private String neispravnosti;
    
    

	public Termin(TerminState stanje, Tehnicar tehnicar, Vozilo vozilo, LocalTime vreme_pocetka,
			LocalDate datum_Termina) {
		super();
		this.stanje = stanje;
		this.tehnicar = tehnicar;
		this.vozilo = vozilo;
		this.vreme_pocetka = vreme_pocetka;
		this.datum_Termina = datum_Termina;
	}

	public TerminState getStanje() {
		return stanje;
	}

	public void setStanje(TerminState stanje) {
		this.stanje = stanje;
	}

	public Tehnicar getTehnicar() {
		return tehnicar;
	}

	public void setTehnicar(Tehnicar tehnicar) {
		this.tehnicar = tehnicar;
	}

	public Vozilo getVozilo() {
		return vozilo;
	}

	public void setVozilo(Vozilo vozilo) {
		this.vozilo = vozilo;
	}

	public LocalTime getVreme_pocetka() {
		return vreme_pocetka;
	}

	public void setVreme_pocetka(LocalTime vreme_pocetka) {
		this.vreme_pocetka = vreme_pocetka;
	}

	public LocalDate getDatum_Termina() {
		return datum_Termina;
	}

	public void setDatum_Termina(LocalDate datum_Termina) {
		this.datum_Termina = datum_Termina;
	}
	
	

    /**
     * 
     */
    
    

}