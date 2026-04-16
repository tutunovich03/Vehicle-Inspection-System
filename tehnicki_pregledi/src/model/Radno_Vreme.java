package model;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * 
 */
public class Radno_Vreme {

    /**
     * Default constructor
     */
    public Radno_Vreme() {
    }

    /**
     * 
     */
    private LocalDate datum;

    /**
     * 
     */
    private LocalTime vreme_od;
    private LocalTime vreme_do;
	public Radno_Vreme(LocalDate datum, LocalTime vreme_od, LocalTime vreme_do) {
		super();
		this.datum = datum;
		this.vreme_od = vreme_od;
		this.vreme_do = vreme_do;
	}
	public LocalDate getDatum() {
		return datum;
	}
	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}
	public LocalTime getVreme_od() {
		return vreme_od;
	}
	public void setVreme_od(LocalTime vreme_od) {
		this.vreme_od = vreme_od;
	}
	public LocalTime getVreme_do() {
		return vreme_do;
	}
	public void setVreme_do(LocalTime vreme_do) {
		this.vreme_do = vreme_do;
	}
	@Override
	public String toString() {
		return "Radno_Vreme [datum=" + datum + ", dan=" + datum.getDayOfWeek() + ", vreme_od=" + vreme_od + ", vreme_do=" + vreme_do + "]";
	}
	
	
    
    

}