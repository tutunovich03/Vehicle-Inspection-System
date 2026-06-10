package model;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class Prekid_Rada {

    @Override
	public String toString() {
		return "Prekid_Rada [id= "+ id +"zaposleni_id=" + zaposleni_id + ", datum_od=" + datum_od + ", datum_do=" + datum_do
				+ ", stanje=" + stanje + ", zaposleni=" + zaposleni + "]";
	}




	/**
     * Default constructor
     */
    public Prekid_Rada() {
    }

    /**
     * 
     */
    protected int zaposleni_id;
    protected LocalDate datum_od;
    protected LocalDate datum_do;
    protected PrekidRadaState stanje;
    protected Zaposleni zaposleni;
    protected int id;

    
    
    
    public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public Zaposleni getZaposleni() {
		return zaposleni;
	}




	public void setZaposleni(Zaposleni zaposleni) {
		this.zaposleni = zaposleni;
	}




	public Prekid_Rada(Zaposleni z, LocalDate datum_od, LocalDate datum_do, PrekidRadaState stanje) {
		super();
		this.datum_od = datum_od;
		this.datum_do = datum_do;
		this.stanje = stanje;
		this.zaposleni = z;
	}
    
    


	public int getZaposleni_id() {
		return zaposleni_id;
	}




	public void setZaposleni_id(int zaposleni_id) {
		this.zaposleni_id = zaposleni_id;
	}



	public LocalDate getDatum_od() {
		return datum_od;
	}






	public void setDatum_od(LocalDate datum_od) {
		this.datum_od = datum_od;
	}






	public LocalDate getDatum_do() {
		return datum_do;
	}






	public void setDatum_do(LocalDate datum_do) {
		this.datum_do = datum_do;
	}






	public PrekidRadaState getStanje() {
		return stanje;
	}






	public void setStanje(PrekidRadaState stanje) {
		this.stanje = stanje;
	}






	public void brojPreostalihDana() {
        // TODO implement here
    }

}