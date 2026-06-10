package model;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import exceptions.VoziloTipException;

/**
 * 
 */
public abstract class Vozilo {

    public Vozilo() {
    	
    }

    protected int id;
	protected VoziloTip tip;
    protected String registarski_broj;
    protected String broj_sasije;
    protected LocalDate datum_isteka_registracije;
    protected Vlasnik vlasnik;
    protected String model;
    protected int cena;

	public int getCena() {
		return cena;
	}


	public void setCena(int cena) {
		this.cena = cena;
	}

	protected Set<VoziloTip> allowed;
    protected abstract void setAllowed();
    

    public Vozilo(VoziloTip tip, String registarski_broj, String broj_sasije, LocalDate datum_isteka_registracije, Vlasnik vlasnik) throws VoziloTipException {
		super();
		setAllowed();
		if(!this.allowed.contains(tip))
			throw new VoziloTipException();
		this.tip = tip;
		this.registarski_broj = registarski_broj;
		this.broj_sasije = broj_sasije;
		this.datum_isteka_registracije = datum_isteka_registracije;
		this.vlasnik = vlasnik;
	}
    
    
    protected double getVlasnikFaktor() {

        switch(this.vlasnik.getTip()) {

            case fizicko_lice:
                return 1.0;

            case pravno_lice:
                return 1.2;

            case auto_skola:
                return 0.9;

            case taksi:
                return 1.15;

            case rent_a_car:
                return 1.3;

            default:
                return 1.0;
        }
    }
    
    

	public abstract int trajanje_tehnickog_min() throws VoziloTipException;
    
    public abstract double cena_registracije() throws VoziloTipException;
    
    public static Vozilo createVozilo(VoziloTip vozilo_tip) {
    	Vozilo v;
    	if(motorna_vozila_types.contains(vozilo_tip)) 
			v = new Motorno_Vozilo();
    	else if(teretna_vozila_types.contains(vozilo_tip))
			v =  new Teretno_Vozilo();
    	else
    		v = new Prikljucno_Vozilo();
    	v.setTip(vozilo_tip);
		
    	return v;
    }
    
    
    private static Set<VoziloTip> motorna_vozila_types = Set.of(
			VoziloTip.MOPED,
			VoziloTip.MOTOCIKL,
			VoziloTip.TRICIKL,
			VoziloTip.CETVOROCIKL,
			VoziloTip.MOTOKULTIVATOR,
			VoziloTip.TRAKTOR,
			VoziloTip.AUTOMOBIL
		);
    private static Set<VoziloTip> teretna_vozila_types = Set.of(
			VoziloTip.LTV,
			VoziloTip.TV_SA_HIDRAUL_KS,
			VoziloTip.TV_SA_PNEUM_KS
		);
    private static Set<VoziloTip> prikljucna_vozila_types = Set.of(
    		VoziloTip.PV_ZA_TRAKTOR,
    		VoziloTip.PV_BKS,
    		VoziloTip.PV_KSIK,
    		VoziloTip.OSTALA_PV
    	);
    

    public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}

	public static Set<VoziloTip> getMotorna_vozila_types() {
		return motorna_vozila_types;
	}


	public static Set<VoziloTip> getTeretna_vozila_types() {
		return teretna_vozila_types;
	}


	public static Set<VoziloTip> getPrikljucna_vozila_types() {
		return prikljucna_vozila_types;
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public VoziloTip getTip() {
		return tip;
	}


	public void setTip(VoziloTip tip) {
		this.tip = tip;
	}

	public String getRegistarski_broj() {
		return registarski_broj;
	}

	public void setRegistarski_broj(String registarski_broj) {
		this.registarski_broj = registarski_broj;
	}

	public String getBroj_sasije() {
		return broj_sasije;
	}

	public void setBroj_sasije(String broj_sasije) {
		this.broj_sasije = broj_sasije;
	}

	public LocalDate getDatum_isteka_registracije() {
		return datum_isteka_registracije;
	}

	public void setDatum_isteka_registracije(LocalDate datum_isteka_registracije) {
		this.datum_isteka_registracije = datum_isteka_registracije;
	}

	public Vlasnik getVlasnik() {
		return vlasnik;
	}

	public void setVlasnik(Vlasnik vlasnik) {
		this.vlasnik = vlasnik;
	}
    
    

}