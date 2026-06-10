package controller;

import model.Korisnik;
import model.KorisnikTip;
import model.Nalog;
import service.GostService;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import exceptions.AccNotFoundException;
import exceptions.UserCreateException;

/**
 * 
 */
public class GostController {
	
	GostService gServ = new GostService();

    /**
     * Default constructor
     */
    public GostController() {
    }

    /**
     * @param email 
     * @param pass 
     * @param broj_telefona 
     * @param ime 
     * @param prezime
     */
    public void addNalog(Nalog n) {
        try {
			gServ.registerUser(n);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * @return
     */
    public boolean validateData() {
        // TODO implement here
        return false;
    }

    /**
     * @param email 
     * @param pass 
     * @return
     */
    public Korisnik getNalog(KorisnikTip tip, String email, String pass) {
    	
    	System.out.println(email + " " + pass);
        
        try {
        	Korisnik k = gServ.autentifikujKorisnika(tip, email, pass);
        	System.out.println(k.toString());
			return k;
		} catch (ClassNotFoundException | SQLException | AccNotFoundException | UserCreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

    /**
     * @param e 
     * @param p
     * @throws Exception 
     */
    public void validateLoginData(String e, String p) throws Exception {
        if(!e.endsWith(".com") || !e.contains("@") || p == "" ) {
        	throw new Exception();
        }
    }
    

}