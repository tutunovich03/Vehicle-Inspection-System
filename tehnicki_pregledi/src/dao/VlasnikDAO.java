package dao;

import model.Vlasnik;

import java.io.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.*;

/**
 * 
 */
public class VlasnikDAO extends DAO{

    /**
     * Default constructor
     */
    public VlasnikDAO() {
    }

    /**
     * @param v
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void createVlasnik(Vlasnik v) throws ClassNotFoundException, SQLException {
        connect();
        try {
	        preparedStatement = con.prepareStatement("INSERT INTO `korisnik`(`name`, `last_name`, `broj_telefona`, `tip_korisnika`)  VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
	        preparedStatement.setString(1, v.getIme());
	        preparedStatement.setString(2, v.getPrezime());
	        preparedStatement.setString(3, v.getBroj_telefona());
	        preparedStatement.setString(4, "vlasnik");
	        
	        preparedStatement.executeUpdate();
        }
        catch(SQLIntegrityConstraintViolationException e){
        	return;
        }
        
        resultSet = preparedStatement.getGeneratedKeys();
        
        resultSet.next();
        int id = resultSet.getInt(1);
        
        preparedStatement = con.prepareStatement("INSERT INTO `vlasnik`(`tip`, `korisnik_id`) VALUES (?,?)");
        preparedStatement.setString(1, v.getTip().toString());
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
        
        v.setId(id);
        
        close();
        
        
    }
    
    
   

}