package controller;

import model.Termin;
import model.Vlasnik;
import model.Vozilo;
import service.GostService;
import service.TerminService;
import service.VoziloService;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import dao.TerminDAO;

/**
 * 
 */
public class SekretaricaController {
	GostService gServ = new GostService();
	VoziloService vServ = new VoziloService();
	TerminService tServ = new TerminService();
	TerminDAO tDAO = new TerminDAO();

    /**
     * Default constructor
     */
    public SekretaricaController() {
    }

    /**
     * @param vreme 
     * @param d 
     * @param v
     */
    public void addTerminSekr(LocalTime vreme, LocalDate d, Vozilo v) {
        // TODO implement here
    }

    /**
     * @param v 
     * @return
     */
    public ArrayList<Termin> getTerminiVlasnika(Vlasnik v) {
    	
        try {
			tDAO.readTerminiByVlanik(v);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
    
    public void addVlasnik(Vlasnik v) {
    	try {
    		gServ.addVlasnik(v);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public ArrayList<Termin> getPregledanaVozilaTerm() {
    	try {
    		return tServ.getPregledanaVozilaTerm();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return null;
    	
    }
    
    public void insertVoziloData(Vozilo v) {
    	try {
    		vServ.insertVoziloData(v);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}   	
    }
    
    public void deleteTermin(Termin t) {
    	
    }
    
    

}