package controller;

import model.Zaposleni;
import service.PrekidRadaService;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class ZaposleniController {

    /**
     * Default constructor
     */
	PrekidRadaService prServ = new PrekidRadaService();
	
	
    public ZaposleniController() {
    }

    /**
     * @param z 
     * @param fp
     */
    public void addBolovanjeZahtev(Zaposleni z, String fp, LocalDate datum_od, LocalDate datum_do) {
        try {
        	prServ.addBolovanjeRequest(z, fp, datum_od, datum_do);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }

    /**
     * @param z 
     * @param od 
     * @param do
     */
    public void addGodisnjiZahtev(Zaposleni z, LocalDate datum_od, LocalDate datum_do) {
    	try {
        	prServ.addGodisnjiRequest(z, datum_od, datum_do);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }

}