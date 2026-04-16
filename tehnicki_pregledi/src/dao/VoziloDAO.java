package dao;

import model.Termin;
import model.Vlasnik;
import model.Vozilo;

import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * 
 */
public class VoziloDAO extends DAO{

    /**
     * Default constructor
     */
    public VoziloDAO() {
    }

    /**
     * @param t 
     * @return
     */
    public ArrayList<String> readCarData(Termin t) {
        // TODO implement here
        return null;
    }

    /**
     * @param v
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void createNewCar(Vozilo v, Vlasnik vl) throws ClassNotFoundException, SQLException {
        
    	connect();
    	preparedStatement = con.prepareStatement("INSERT INTO `vozilo`(`tip`, `datum_isteka_registracije`, `vlasnik_id`) VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);
    	preparedStatement.setString(1, v.getTip().toString());
    	preparedStatement.setObject(2, v.getDatum_isteka_registracije());
    	preparedStatement.setInt(3, vl.getId());
    	
    	preparedStatement.executeUpdate();
    	
    	resultSet = preparedStatement.getGeneratedKeys();
    	
    	resultSet.next();
    	
    	v.setId(resultSet.getInt(1));
    	
    	close();
    }
    
    public void removeVozilo(Vozilo v) {
    	
    }

}