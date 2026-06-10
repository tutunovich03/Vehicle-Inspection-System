package service;

import model.Motorno_Vozilo;
import model.Vlasnik;
import model.Vozilo;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import dao.VoziloDAO;
import exceptions.RegDateException;
import exceptions.VoziloTipException;

/**
 * 
 */
public class VoziloService {

    /**
     * Default constructor
     */
    public VoziloService() {
    }

    /**
     * @param v
     * @throws VoziloTipException 
     * @throws RegDateException 
     */
    VoziloDAO vDAO = new VoziloDAO();
    
    
    public void verifyVozilo(Vozilo v, LocalDate date) {
    	
    }

    /**
     * @param v
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void addVozilo(Vozilo v, Vlasnik vl) throws ClassNotFoundException, SQLException {
        vDAO.createNewCar(v,vl);
    }
    
    public void insertVoziloData(Vozilo v) throws ClassNotFoundException, SQLException {
    	vDAO.insertVoziloData(v);
    }

}