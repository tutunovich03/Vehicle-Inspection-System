package dao;

import model.Motorno_Vozilo;
import model.RadnoMestoZaposlenog;
import model.Sekretarica;
import model.Tehnicar;
import model.Termin;
import model.TerminState;
import model.Zaposleni;

import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import exceptions.UserCreateException;

/**
 * 
 */
public class ZaposleniDAO extends DAO{

    /**
     * Default constructor
     */
    public ZaposleniDAO() {
    	
    }

    /**
     * @param z 
     * @param radno_mesto
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void createZaposleni(Zaposleni z, RadnoMestoZaposlenog radno_mesto) throws ClassNotFoundException, SQLException {
        connect();
        preparedStatement = con.prepareStatement("INSERT INTO `korisnik`(`name`, `last_name`, `broj_telefona`, `tip_korisnika`) VALUES (?,?,?,'zaposleni')", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, z.getIme());
        preparedStatement.setString(2, z.getPrezime());
        preparedStatement.setString(3, z.getBroj_telefona());
        
        preparedStatement.executeUpdate();
        resultSet = preparedStatement.getGeneratedKeys();
        
        resultSet.next();
        
        int id = resultSet.getInt(1);
        
        preparedStatement = con.prepareStatement("INSERT INTO `zaposleni`(`radno_mesto`, `plata_mesec`, `br_dana_god_odm`, `preostali_dani_godm`, `grupa_id`, `korisnik_id`, `pauza`) VALUES (?,?,?,?,?,?,?)");
        preparedStatement.setString(1, radno_mesto.toString());
        preparedStatement.setInt(2, z.getPlata());
        preparedStatement.setInt(3, z.getBroj_dana_godisnjeg());
        preparedStatement.setInt(4, z.getPreostali_dani_godisnjeg());
        preparedStatement.setInt(5, z.getGrupa());
        preparedStatement.setInt(6, id);
        preparedStatement.setInt(7, z.getRedPauze());
        
        preparedStatement.executeUpdate();
        close();
        
    }

    /**
     * @param z
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void deleteZaposleni(Zaposleni z) throws ClassNotFoundException, SQLException {
        connect();
        preparedStatement = con.prepareStatement("DELETE FROM `korisnik` WHERE `id` = ?");
        preparedStatement.setInt(1, z.getId());
        preparedStatement.executeUpdate();
        close();
        
    }

    /**
     * @return
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws UserCreateException 
     */
    public ArrayList<Zaposleni> readZapsoleniList(RadnoMestoZaposlenog rm) throws SQLException, ClassNotFoundException, UserCreateException {
    	String condition = "";
    	if(rm != null) {
    		condition = " WHERE `radno_mesto` = ";
    		if(rm == RadnoMestoZaposlenog.TEHNICAR) {
    			condition += "'tehnicar'";
    		}
    		else {
    			condition += "'sekretarica'";
    		}
    	}
    	
    	ArrayList<Zaposleni> zaposleni = new ArrayList<Zaposleni>();
    	connect();
		statement = con.createStatement();
		resultSet = statement.executeQuery("SELECT zaposleni.*, korisnik.* FROM `korisnik` JOIN zaposleni ON korisnik.id = zaposleni.korisnik_id" + condition);
		while(resultSet.next()) {
			String radno_mesto_str = resultSet.getString(1);
			int plata = resultSet.getInt(2);
			int br_dana_god = resultSet.getInt(3);
			int preostali_dani_god = resultSet.getInt(4);
			int grupa = resultSet.getInt(5);
			int pauza = resultSet.getInt(7);
			int id = resultSet.getInt(8);
			String ime = resultSet.getString(9);
			String prezime = resultSet.getString(10);
			String br_telefona = resultSet.getString(11).replace(" ", "");
			
			RadnoMestoZaposlenog rmz = RadnoMestoZaposlenog.valueOf(radno_mesto_str.toUpperCase());
			
			Zaposleni z = null;
			
			if(rmz == RadnoMestoZaposlenog.TEHNICAR)
				z = new Tehnicar(id, ime, prezime, br_telefona, plata, br_dana_god, preostali_dani_god, grupa, pauza);
			else
				z = new Sekretarica(id, ime, prezime, br_telefona, plata, br_dana_god, preostali_dani_god, grupa, pauza);
			
			zaposleni.add(z);
			
		}
		close();
        return zaposleni;
    }

}