package model;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * 
 */
public class Radno_Vreme_Zaposlenog extends Radno_Vreme {

    /**
     * Default constructor
     */
    
    

    /**
     * 
     */
    private Zaposleni zaposleni;



	public Radno_Vreme_Zaposlenog(LocalDate datum, LocalTime vreme_od, LocalTime vreme_do, Zaposleni z) {
		super(datum, vreme_od, vreme_do);
		this.zaposleni = z;
	}



	public Zaposleni getZaposleni() {
		return zaposleni;
	}



	public void setZaposleni(Zaposleni z) {
		this.zaposleni = z;
	}



	@Override
	public String toString() {
		return super.toString() + " [zaposleni=" + zaposleni + "]";
	}
	
	

	
    
    

}