package controller;

import model.Zaposleni;
import service.PrekidRadaService;
import service.RadnoVremeService;
import service.SmenaService;
import service.ZaposleniService;
import model.Prekid_Rada;

import java.io.*;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

import dao.SmenaDAO;
import exceptions.RedPauzeException;
import exceptions.UserCreateException;

/**
 * 
 */
public class AdminController {

	ZaposleniService zServ = new ZaposleniService();
	RadnoVremeService rvServ = new RadnoVremeService();
	SmenaService smenaServ = new SmenaService();
	PrekidRadaService prServ = new PrekidRadaService();
	SmenaDAO sDAO = new SmenaDAO();
    /**
     * Default constructor
     */
    public AdminController() {
    }

    /**
     * @param z
     */
    public void addZaposleni(Zaposleni z) {
        try {
			zServ.addZaposleni(z);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * @param z
     */
    public void removeZaposleni(Zaposleni z) {
    	try {
			zServ.removeZaposleni(z);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * @return
     */
    public ArrayList<Zaposleni> getZaposleniList() {
        ArrayList<Zaposleni> lista = new ArrayList<Zaposleni>();
        
        try {
			lista = zServ.zaposleniList(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return lista;
    }

    /**
     * @return
     */
    public ArrayList<Prekid_Rada> prikaziZahteveZaPrekidRada() {
    	ArrayList<Prekid_Rada> lista = new ArrayList<Prekid_Rada>();
        try {
			lista = prServ.getPrekidiRadaZahtevi();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return lista;
    }

    /**
     * @param accept
     */
    public void confirmPrekidRada(boolean accept, Prekid_Rada pr) {
        try {
			prServ.confirmPrekidRada(accept, pr);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * 
     */
    public void setRadnoVremeDana(DayOfWeek day, LocalTime vreme_od, LocalTime vreme_do) {
        try {
			rvServ.setRV_Dana(day, vreme_od, vreme_do);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * @param p
     */
    public void addPrekidRada(Prekid_Rada p) {
        try {
			prServ.addPrekidRada(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * 
     */
    public void setRadnoVremeDatuma(String datum, LocalTime vreme_od, LocalTime vreme_do, boolean stalno) {
    	try {
			rvServ.setRV_Datuma(datum, vreme_od, vreme_do, stalno);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * @param brSmena 
     * @param smene[]
     */
    public void setBrojSmena(int brSmena) {
        try {
			smenaServ.setBrojSmena(brSmena);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void setZaposleniGrupa(Zaposleni zap, int grupa) {
    	try {
			smenaServ.setZaposleniGrupa(zap, grupa);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void setRedPauze(Zaposleni zap, int red) {
    	try {
			rvServ.set_Red_Pauze(zap, red);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void setTrajanjePauze(int minutes) {
    	try {
			rvServ.set_Trajanje_Pauze(minutes);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public int getBrojSmena() {
    	try {
			return sDAO.readBrojSmena();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
    }

}