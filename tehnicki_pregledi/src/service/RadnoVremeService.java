package service;

import java.io.*;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import dao.Radno_Vreme_DAO;
import dao.ZaposleniDAO;
import exceptions.RedPauzeException;
import exceptions.TrajanjePauzeException;
import exceptions.UserCreateException;
import model.RadnoMestoZaposlenog;
import model.Radno_Vreme;
import model.Sekretarica;
import model.Tehnicar;
import model.Zaposleni;

/**
 * 
 */
public class RadnoVremeService {

    /**
     * Default constructor
     */
	Radno_Vreme_DAO rvDAO = new Radno_Vreme_DAO();
	ZaposleniDAO zDAO = new ZaposleniDAO();
	
	
    public RadnoVremeService() {
    }

    /**
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * 
     */
    public void setRV_Dana(DayOfWeek day, LocalTime vreme_od, LocalTime vreme_do) throws ClassNotFoundException, SQLException {
        rvDAO.UpdateRV_Dan(day, vreme_od, vreme_do);
    }

    /**
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * 
     */
    public void setRV_Datuma(String datum, LocalTime vreme_od, LocalTime vreme_do, boolean stalno) throws ClassNotFoundException, SQLException { //datum mora biti string posto idu samo mesec i dan, ne godina
        rvDAO.updateRV_Datuma(datum, vreme_od, vreme_do, stalno);
    }
    
    private void verify_Red_Pauze(Zaposleni zap, int red) throws ClassNotFoundException, SQLException, UserCreateException, RedPauzeException {
    	ArrayList<Zaposleni> lista = null;
    	int broj_zaposlenih=0;
    	if(zap instanceof Tehnicar) {
    		lista = zDAO.readZapsoleniList(RadnoMestoZaposlenog.TEHNICAR);
    	}
    	if(zap instanceof Sekretarica) {
    		lista = zDAO.readZapsoleniList(RadnoMestoZaposlenog.SEKRETARICA);
    	}
    	for (Zaposleni zaposleni : lista) {
			if(zaposleni.getGrupa() == zap.getGrupa())
				broj_zaposlenih++;
		}
    	
	    if(red<=0 || red>broj_zaposlenih) {
	    	throw new RedPauzeException();
	    }
    }
    
    public void set_Red_Pauze(Zaposleni zap, int red) throws ClassNotFoundException, SQLException, UserCreateException, RedPauzeException {
    	verify_Red_Pauze(zap, red);
    	
    	rvDAO.updateRedPauzeZaposleni(zap, red);
    }
    
    public void set_Trajanje_Pauze(int minutes) throws TrajanjePauzeException, ClassNotFoundException, SQLException {
    	if(minutes<10 || minutes>45)
    		throw new TrajanjePauzeException();
    	rvDAO.updateTrajanjePauze(minutes);
    }

}