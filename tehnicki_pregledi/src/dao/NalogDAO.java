package dao;

import model.Admin;
import model.Nalog;
import model.Sekretarica;
import model.Tehnicar;
import model.Vlasnik;
import model.VlasnikTip;
import model.Zaposleni;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import exceptions.AccNotFoundException;
import exceptions.UserCreateException;

/**
 * 
 */
public class NalogDAO extends DAO{

    /**
     * Default constructor
     */
    public NalogDAO() {
    }

    /**
     * @return
     */
    public boolean getMail() {
        // TODO implement here
        return false;
    }

    /**
     * @param e 
     * @param p 
     * @return
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws AccNotFoundException 
     */
    public Vlasnik readVlasnik(String e, String p) throws ClassNotFoundException, SQLException, AccNotFoundException {
        
    	connect();
    	
    	preparedStatement = con.prepareStatement("SELECT korisnik.name, korisnik.last_name, korisnik.broj_telefona, vlasnik.tip, korisnik.id FROM `nalog` JOIN korisnik on korisnik.id = nalog.korisnik_id JOIN vlasnik ON vlasnik.korisnik_id = korisnik.id WHERE email = ? AND lozinka = ? AND tip_korisnika = \"vlasnik\";");
    	preparedStatement.setString(1, e);
    	preparedStatement.setString(2, p);
    	
    	resultSet = preparedStatement.executeQuery();
    	
    	
    	if(resultSet.next()) {
    		
    		String name = resultSet.getString(1);
    		String ln = resultSet.getString(2);
    		String phone = resultSet.getString(3).replace(" ", "");
    		String tip_str = resultSet.getString(4);
    		int id = resultSet.getInt(5);
    		
    		VlasnikTip tip = VlasnikTip.valueOf(tip_str);
    		
    		Vlasnik vl = new Vlasnik();
    		vl.setId(id);
    		vl.setBroj_telefona(phone);
    		vl.setIme(name);
    		vl.setPrezime(ln);
    		vl.setTip(tip);
    		close();
    		return vl;
    		
    	}	
    	
    	throw new AccNotFoundException();

    }
    
    public Admin readAdmin(String e, String p) throws SQLException, AccNotFoundException, ClassNotFoundException {
        
    	connect();
    	
    	preparedStatement = con.prepareStatement("SELECT korisnik.name, korisnik.last_name, korisnik.broj_telefona, korisnik.id FROM `nalog` JOIN korisnik on korisnik.id = nalog.korisnik_id WHERE email = ? AND lozinka = ? AND tip_korisnika = \"admin\";");
    	preparedStatement.setString(1, e);
    	preparedStatement.setString(2, p);
    	
    	resultSet = preparedStatement.executeQuery();
    	
    	
    	if(resultSet.next()) {
    		
    		String name = resultSet.getString(1);
    		String ln = resultSet.getString(2);
    		String phone = resultSet.getString(3).replace(" ", "");
    		int id = resultSet.getInt(4);
    		
    		Admin vl = new Admin();
    		vl.setId(id);
    		vl.setBroj_telefona(phone);
    		vl.setIme(name);
    		vl.setPrezime(ln);
    		close();
    		return vl;
    		
    	}	
    	
    	throw new AccNotFoundException();
   
    }
    
    public Zaposleni readZaposleni(String e, String p) throws SQLException, ClassNotFoundException, AccNotFoundException, UserCreateException {
    	
    	connect();
    	
    	preparedStatement = con.prepareStatement("SELECT korisnik.name, korisnik.last_name, korisnik.broj_telefona, korisnik.id, zaposleni.* FROM `nalog` JOIN korisnik on korisnik.id = nalog.korisnik_id join zaposleni on zaposleni.korisnik_id = korisnik.id WHERE email = ? AND lozinka = ? AND tip_korisnika = \"zaposleni\";");
    	preparedStatement.setString(1, e);
    	preparedStatement.setString(2, p);
    	
    	resultSet = preparedStatement.executeQuery();
    	
    	
    	if(resultSet.next()) {
    		
    		String name = resultSet.getString(1);
    		String ln = resultSet.getString(2);
    		String phone = resultSet.getString(3).replace(" ", "");
    		int id = resultSet.getInt(4);
    		String radnoMesto = resultSet.getString(5);
    		int plata = resultSet.getInt(6);
    		int godOdm = resultSet.getInt(7);
    		int godOdmPr = resultSet.getInt(8);
    		int grupa = resultSet.getInt(9);
    		int pauza = resultSet.getInt(11);
    		
    		Zaposleni vl;
    		if(radnoMesto.equals("tehnicar") )
    			vl = new Tehnicar(id, name, ln, phone, plata, godOdm, godOdmPr, grupa, pauza);
    		else
    			vl = new Sekretarica(id, name, ln, phone, plata, godOdm, godOdmPr, grupa, pauza);
    		
    		close();
    			
    		return vl;
    		
    	}	
    	
    	throw new AccNotFoundException();
        
    	
    }
    
    public void createNalog(Nalog n) throws ClassNotFoundException, SQLException {
    	connect();
    	preparedStatement = con.prepareStatement("INSERT INTO `nalog`(`email`, `lozinka`, `korisnik_id`) VALUES (?,?,?)");
    	
    	preparedStatement.setString(1, n.getMail());
    	preparedStatement.setString(2, n.getLozinka());
    	preparedStatement.setInt(3, n.getKorisnik().getId());
    	
    	close();
    }
    
    
    

}