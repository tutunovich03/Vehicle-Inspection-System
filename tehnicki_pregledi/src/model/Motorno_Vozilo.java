package model;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import exceptions.UserCreateException;
import exceptions.VoziloTipException;

/**
 * 
 */
public class Motorno_Vozilo extends Vozilo {

    public Motorno_Vozilo() {
    }

    protected int zapremina_motora;
    protected int snagaKW;
	
    protected void setAllowed() {
    	this.allowed = Vozilo.getMotorna_vozila_types();
    }
	
	public Motorno_Vozilo(VoziloTip tip, String registarski_broj, String broj_sasije, LocalDate datum_isteka_registracije,
			Vlasnik vlasnik, int zapremina_motora, int snagaKW) throws VoziloTipException {
		super(tip,registarski_broj, broj_sasije, datum_isteka_registracije, vlasnik);
		this.zapremina_motora = zapremina_motora;
		this.snagaKW = snagaKW;
	}


	


	public int trajanje_tehnickog_min() throws VoziloTipException{ 
		int trajanje = switch (tip) {
			case MOPED, MOTOCIKL, TRICIKL, CETVOROCIKL, MOTOKULTIVATOR ->  20;
			case TRAKTOR -> 25;
			case AUTOMOBIL -> 35;
			default -> throw new VoziloTipException();
		};
		
		return trajanje;
		
		
	}
	
	
	public int cena_registracije() {
		return 0;
	}
	
	
	
	
	
	public int getZapremina_motora() {
		return zapremina_motora;
	}
	public void setZapremina_motora(int zapremina_motora) {
		this.zapremina_motora = zapremina_motora;
	}
	public int getSnagaKW() {
		return snagaKW;
	}
	public void setSnagaKW(int snagaKW) {
		this.snagaKW = snagaKW;
	}

}