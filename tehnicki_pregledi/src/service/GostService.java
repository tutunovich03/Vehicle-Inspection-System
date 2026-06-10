package service;

import model.Korisnik;
import model.KorisnikTip;
import model.Nalog;
import model.Vlasnik;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import dao.NalogDAO;
import dao.VlasnikDAO;
import exceptions.AccNotFoundException;
import exceptions.UserCreateException;

/**
 * 
 */
public class GostService {

    /**
     * Default constructor
     */
	VlasnikDAO vDAO = new VlasnikDAO();
	NalogDAO nDAO = new NalogDAO();
	
	
    public GostService() {
    }

    /**
     * @param k
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void registerUser(Nalog n) throws ClassNotFoundException, SQLException {
        checkPassword(n.getLozinka());
        
        nDAO.createNalog(n);
        
    }

    /**
     * 
     */
    public void checkPassword(String pass) {
    	if (pass == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
    	
        String regex = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{9,}$";

        if (!pass.matches(regex)) {
            throw new IllegalArgumentException("String must be > 8 characters, " +
                    "contain an uppercase letter, and a special character.");
        }
    }
    

    /**
     * @param e 
     * @param p 
     * @return
     * @throws AccNotFoundException 
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws UserCreateException 
     */
    public Korisnik autentifikujKorisnika(KorisnikTip tip, String e, String p) throws ClassNotFoundException, SQLException, AccNotFoundException, UserCreateException {
        
    	Korisnik k = null;
    	
    	switch(tip) {
    		case ADMIN: k = nDAO.readAdmin(e, p);break;
    		case VLASNIK: k = nDAO.readVlasnik(e, p);break;
    		case ZAPOSLENI: k = nDAO.readZaposleni(e, p);break;
    	}
    	
        return k;
    }
    
    public void addVlasnik(Vlasnik v) throws ClassNotFoundException, SQLException {
    	vDAO.createVlasnik(v);
    }

}