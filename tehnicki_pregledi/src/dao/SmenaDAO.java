package dao;


import java.io.*;
import java.sql.SQLException;
import java.util.*;

import model.Zaposleni;

/**
 * 
 */
public class SmenaDAO extends DAO {

    /**
     * Default constructor
     */
    public SmenaDAO() {
    }

    /**
     * @param s[]
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void addSmena() throws ClassNotFoundException, SQLException {
        connect();
        int poslednjaGrupa;
        statement = con.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM grupa ORDER BY id DESC LIMIT 1;");
        resultSet.next();
        poslednjaGrupa = resultSet.getInt(1);
        
        statement = con.createStatement();
        statement.executeUpdate("INSERT INTO `grupa`(`id`) VALUES ("+ (poslednjaGrupa+1) +")");
        close();
    }

    /**
     * @param n
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void deleteSmena() throws ClassNotFoundException, SQLException {
        connect();
        int poslednjaGrupa;
        statement = con.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM grupa ORDER BY id DESC LIMIT 1;");
        resultSet.next();
        poslednjaGrupa = resultSet.getInt(1);
        
        preparedStatement = con.prepareStatement("UPDATE `zaposleni` SET `grupa_id`= ? WHERE `grupa_id` = ?;");
        preparedStatement.setInt(1, poslednjaGrupa-1);
        preparedStatement.setInt(2, poslednjaGrupa);
        preparedStatement.executeUpdate();
        
        preparedStatement = con.prepareStatement("DELETE FROM `grupa` WHERE `id` = ?");
        preparedStatement.setInt(1, poslednjaGrupa);
        preparedStatement.executeUpdate();
        
        close();
        
    }

    /**
     * @param s[]
     */
    public void updateSmena() {
        // TODO implement here
    }
    
    public int readBrojSmena() throws ClassNotFoundException, SQLException {
    	connect();
    	statement = con.createStatement(); 
    	resultSet = statement.executeQuery("SELECT COUNT(*) FROM grupa;");
    	resultSet.next();
    	
    	int brSmena = resultSet.getInt(1);
    	
    	close();
    	return brSmena;
    }
    
    public void updateZaposleniGrupa(Zaposleni z, int grupa) throws ClassNotFoundException, SQLException {
    	connect();
    	preparedStatement = con.prepareStatement("UPDATE `zaposleni` SET `grupa_id`=? WHERE `korisnik_id`=?");
    	preparedStatement.setInt(1, grupa);
    	preparedStatement.setInt(2, z.getId());
    	preparedStatement.executeUpdate();
    	close();
    	
    }
    
    

}