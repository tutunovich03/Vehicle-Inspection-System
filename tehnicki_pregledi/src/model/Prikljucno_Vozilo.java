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
	
	public int cena_registracije() {
		return 0;
	}
	
	
	
	public int getNosivost() {
		return nosivost;
	}
	public void setNosivost(int nosivost) {
		this.nosivost = nosivost;
	}
	
    

}