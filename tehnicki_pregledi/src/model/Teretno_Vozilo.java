package model;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import exceptions.VoziloTipException;

/**
 * 
 */
public class Teretno_Vozilo extends Motorno_Vozilo {

    /**
     * Default constructor
     */
    public Teretno_Vozilo() {
    }
    
    private int najveca_dozvoljena_masa;
    
    protected void setAllowed() {
    	this.allowed = Vozilo.getTeretna_vozila_types();
    }
    
    public Teretno_Vozilo(VoziloTip tip, String registarski_broj, String broj_sasije, LocalDate datum_isteka_registracije,
			Vlasnik vlasnik, int zapremina_motora, int snagaKW, int najveca_dozvoljena_masa) throws VoziloTipException {
		super(tip, registarski_broj, broj_sasije, datum_isteka_registracije, vlasnik, zapremina_motora, snagaKW);
		this.najveca_dozvoljena_masa = najveca_dozvoljena_masa;
	}
    
    
    public int trajanje_tehnickog_min() throws VoziloTipException {
    	int trajanje = switch(tip) {
    	case TV_SA_HIDRAUL_KS -> 40;
    	case TV_SA_PNEUM_KS -> 55;
    	default -> throw new VoziloTipException();
    	};
    	return trajanje;
    }
    
    public double cena_registracije() throws VoziloTipException {
    	double cena = 10000;
        
        cena += this.getZapremina_motora() * 3;
        
        cena += this.getSnagaKW() * 20;
       
        cena += this.getNajveca_dozvoljena_masa() * 4;

        
        switch(this.getTip()) {

            case LTV:
                cena += 5000;
                break;

            case TV_SA_HIDRAUL_KS:
                cena += 8000;
                break;

            case TV_SA_PNEUM_KS:
                cena += 10000;
                break;
                
            default: 
            	throw new VoziloTipException();
            	
            
        }

        cena *= getVlasnikFaktor();

        return cena;
    }
    
    

	public int getNajveca_dozvoljena_masa() {
		return najveca_dozvoljena_masa;
	}

	public void setNajveca_dozvoljena_masa(int najveca_dozvoljena_masa) {
		this.najveca_dozvoljena_masa = najveca_dozvoljena_masa;
	}

    




	

}