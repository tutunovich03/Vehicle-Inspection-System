package dao;

import model.Zaposleni;
import model.Bolovanje;
import model.Godisnji_Odmor;
import model.PrekidRadaState;
import model.Prekid_Rada;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 
 */
public class Prekid_Rada_DAO extends DAO{

    /**
     * Default constructor
     */
    public Prekid_Rada_DAO() {
    }

    /**
     * @param z 
     * @param fp
     */
    public void createBolovanjeRequest(Bolovanje b) {
        // TODO implement here
    }

    /**
     * @param z 
     * @param od 
     * @param do
     */
    public void createOdmorRequest(Zaposleni z, LocalDate datum_od, LocalDate datum_do) {
        // TODO implement here
    }

    /**
     * @return
     */
    public ArrayList<Bolovanje> readBolovanjaRequests() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public ArrayList<Godisnji_Odmor> readGodOdmRequests() {
        // TODO implement here
        return null;
    }

    /**
     * @param state
     */
    public void updatePrekidRada(int state) {
        // TODO implement here
    }

    /**
     * @param p
     */
    public void createPrekidRada(Prekid_Rada p) {
        // TODO implement here
    }
    
    public ArrayList<Prekid_Rada> readPrekidRada(PrekidRadaState ps) {
    	return null;
    }
    
    public ArrayList<Prekid_Rada> readPrekidRadaUToku(LocalDate d) throws ClassNotFoundException, SQLException { // uzima prekide rada kojima je stanje PRIHVACEN i kojima datum_do >= datum && datum >= datum_od
    	ArrayList<Prekid_Rada> prekidiRada = new ArrayList<Prekid_Rada>();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd");
    	String formattedDate = d.format(formatter);
    	
    	
    	connect();
    	preparedStatement = con.prepareStatement("SELECT * FROM `prekid_rada` WHERE `stanje`=\"prihvacen\" AND `datum_od`<= ? AND `datum_do`>= ?;");
    	preparedStatement.setString(1, formattedDate);
    	preparedStatement.setString(2, formattedDate);
    	
    	resultSet = preparedStatement.executeQuery();
    	while(resultSet.next()) {
    		Prekid_Rada pr = new Prekid_Rada();
    		pr.setDatum_od(resultSet.getObject(1,LocalDate.class));
    		pr.setDatum_do(resultSet.getObject(2,LocalDate.class));
    		pr.setZaposleni_id(resultSet.getInt(4));
    		prekidiRada.add(pr);
    	}
    	
    	close();
    	return prekidiRada;
    }
    

}