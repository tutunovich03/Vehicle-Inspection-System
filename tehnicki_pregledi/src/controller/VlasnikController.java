package controller;

import model.Vozilo;
import service.TerminService;
import service.VoziloService;
import model.Nalog;
import model.Termin;
import model.TerminState;
import model.Vlasnik;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import exceptions.RegDateException;
import exceptions.TerminZauzetException;
import exceptions.UserCreateException;
import exceptions.VoziloTipException;

/**
 * 
 */
public class VlasnikController {
	
	TerminService termServ = new TerminService();
	VoziloService vozServ = new VoziloService(); 

    /**
     * Default constructor
     */
    public VlasnikController() {
    }

    /**
     * @param d 
     * @param v 
     * @return
     */
    public ArrayList<LocalTime> getSlobTerm(LocalDate d, Vozilo v) {
    	ArrayList<LocalTime> lista = null;
    	try {
    		lista = termServ.getSlobodniTermini(d, v);
        }
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return lista;
    }

    /**
     * @param n 
     * @return
     */
    public ArrayList<Termin> getTermini(Vlasnik v, TerminState ts) {
    	ArrayList<Termin> lista = null;
    	try {
    		lista = termServ.getTermini(v, ts);
        }
    	catch(Exception e) {
    		e.printStackTrace();
    	}
        return lista;
    }

    /**
     * @param t
     */
    public void removeTermin(Termin t) {
    	try {
        termServ.removeTermin(t);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    /**
     * @param Vlasnik 
     * @return
     */
    public ArrayList<Termin> getIstorijaPregleda(Vlasnik v) {
        // TODO implement here
        return null;
    }

    /**
     * @param vreme 
     * @param d 
     * @param v
     * @throws SQLException 
     * @throws RegDateException 
     * @throws VoziloTipException 
     * @throws TerminZauzetException 
     * @throws ClassNotFoundException 
     * @throws UserCreateException 
     */
    public void addTerminVozilo(LocalTime vreme, LocalDate d, Vozilo v, Vlasnik vl) {
    	try {
        termServ.addTermin(vreme, d, v, vl);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }

}