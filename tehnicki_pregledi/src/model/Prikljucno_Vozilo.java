package model;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import exceptions.VoziloTipException;

/**
 * 
 */
public class Prikljucno_Vozilo extends Vozilo {

    public Prikljucno_Vozilo() {
    }
    
    private int nosivost;
    
    protected void setAllowed() {
    	this.allowed = Vozilo.getPrikljucna_vozila_types();
    }
    
	public Prikljucno_Vozilo(VoziloTip tip, String registarski_broj, String broj_sasije, LocalDate datum_isteka_registracije,
			Vlasnik vlasnik, int nosivost) throws VoziloTipException {
		super(tip, registarski_broj, broj_sasije, datum_isteka_registracije, vlasnik);
		this.nosivost = nosivost;
	}
	
	public int trajanje_tehnickog_min() throws VoziloTipException {
		int trajanje = switch (tip) {
		case PV_ZA_TRAKTOR,PV_BKS,PV_KSIK -> 20;
		case OSTALA_PV ->  30;
		default -> throw new VoziloTipException();
		};
		return trajanje;
	}
	
	public double cena_registracije() throws VoziloTipException {
		double cena = 4000;

	    cena += this.getNosivost() * 5;

	    switch(this.getTip()) {

	        case PV_ZA_TRAKTOR:
	            cena += 1000;
	            break;

	        case PV_BKS:
	            cena += 2000;
	            break;

	        case PV_KSIK:
	            cena += 2500;
	            break;

	        case OSTALA_PV:
	            cena += 3000;
	            break;
	            
	        default:
	        	throw new VoziloTipException();
	    }

	    cena *= getVlasnikFaktor();

	    return cena;
	}
	
	
	
	public int getNosivost() {
		return nosivost;
	}
	public void setNosivost(int nosivost) {
		this.nosivost = nosivost;
	}
	
    

}