package dao;

import model.Motorno_Vozilo;
import model.Prikljucno_Vozilo;
import model.Teretno_Vozilo;
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
    
    public void insertVoziloData(Vozilo v) throws SQLException, ClassNotFoundException {
    	connect();
    	preparedStatement = con.prepareStatement("UPDATE `vozilo` SET `reg_oznaka`=?,`broj_sasije`=?,`model`=? WHERE vozilo_id = ?");
    	preparedStatement.setString(1, v.getRegistarski_broj());
    	preparedStatement.setString(2, v.getBroj_sasije());
    	preparedStatement.setString(3, v.getModel());
    	preparedStatement.setInt(4, v.getId());
    	
    	preparedStatement.executeUpdate();
    	
    	if(v instanceof Motorno_Vozilo) {
    		preparedStatement = con.prepareStatement("UPDATE `motorno_vozilo` SET `snagaKW`=? ,`zapremina_motora`=? WHERE `vozilo_id` = ?");
    		preparedStatement.setInt(1,((Motorno_Vozilo)v).getSnagaKW());
    		preparedStatement.setInt(2,((Motorno_Vozilo)v).getZapremina_motora());
    		preparedStatement.setInt(3,v.getId());
    	}
    	
    	if(v instanceof Teretno_Vozilo) {
    		preparedStatement = con.prepareStatement("UPDATE `teretno_vozilo` SET `max_dozv_masa`=? WHERE `vozilo_id` = ?");
    		preparedStatement.setInt(1,((Teretno_Vozilo)v).getNajveca_dozvoljena_masa());
    		preparedStatement.setInt(1,v.getId());
    	}
    	
    	if(v instanceof Prikljucno_Vozilo) {
    		preparedStatement = con.prepareStatement("UPDATE `prikljucno_vozilo` SET `nosivost`=? WHERE `vozilo_id` = ?");
    		preparedStatement.setInt(1,((Teretno_Vozilo)v).getNajveca_dozvoljena_masa());
    		preparedStatement.setInt(1,v.getId());
    	}
    	
    }

}