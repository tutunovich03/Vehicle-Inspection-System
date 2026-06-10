package dao;

import model.Zaposleni;
import model.Bolovanje;
import model.Godisnji_Odmor;
import model.PrekidRadaState;
import model.Prekid_Rada;
import model.Sekretarica;
import model.Tehnicar;

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
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void createBolovanjeRequest(Bolovanje b) throws ClassNotFoundException, SQLException {
        connect();
        preparedStatement = con.prepareStatement("INSERT INTO `prekid_rada`(`datum_od`, `datum_do`, `stanje`, `zaposleni_id`, `tip_prekida`) VALUES (?,?,'zahteva_se',?,'bolovanje')", java.sql.Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setObject(1, b.getDatum_od());
        preparedStatement.setObject(2, b.getDatum_do());
        preparedStatement.setInt(3, b.getZaposleni().getId());
        preparedStatement.executeUpdate();
        
        resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        
        int id = resultSet.getInt(1);
        
        preparedStatement = con.prepareStatement("INSERT INTO `bolovanje`(`file`, `prekid_rada_id`) VALUES (?,?)");
        preparedStatement.setString(1, b.getFilePath());
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
        
        close();
        
        
    }

    /**
     * @param z 
     * @param od 
     * @param do
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void createOdmorRequest(Zaposleni z, LocalDate datum_od, LocalDate datum_do) throws ClassNotFoundException, SQLException {
    	connect();
        preparedStatement = con.prepareStatement("INSERT INTO `prekid_rada`(`datum_od`, `datum_do`, `stanje`, `zaposleni_id`, `tip_prekida`) VALUES (?,?,'zahteva_se',?,'godisnji')", java.sql.Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setObject(1, datum_od);
        preparedStatement.setObject(2, datum_do);
        preparedStatement.setInt(3, z.getId());
        preparedStatement.executeUpdate();
        
        close();
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
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void updatePrekidRada(PrekidRadaState state, Prekid_Rada pr) throws ClassNotFoundException, SQLException {
        connect();
        preparedStatement = con.prepareStatement("UPDATE `prekid_rada` SET `stanje`=? WHERE id = ?");
        preparedStatement.setString(1, state.toString());
        preparedStatement.setInt(2, pr.getId());
        preparedStatement.executeUpdate();
        
        close();
    }

    /**
     * @param p
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void createPrekidRada(Prekid_Rada p) throws ClassNotFoundException, SQLException {
        connect();
        preparedStatement = con.prepareStatement("INSERT INTO `prekid_rada`(`datum_od`, `datum_do`, `stanje`, `zaposleni_id`, `tip_prekida`) VALUES (?,?,?,?,?)");
        preparedStatement.setObject(1, p.getDatum_od());
        preparedStatement.setObject(2, p.getDatum_do());
        preparedStatement.setString(3, p.getStanje().toString());
        preparedStatement.setInt(4, p.getZaposleni().getId());
        if(p instanceof Godisnji_Odmor || p instanceof Bolovanje)
        	preparedStatement.setString(5, p.getClass().getSimpleName().toLowerCase());
        else
        	preparedStatement.setString(5, "neplanirani");
        
        preparedStatement.executeUpdate();
        
        close();
    }
    
    public ArrayList<Prekid_Rada> readPrekidRada(PrekidRadaState ps) throws SQLException, ClassNotFoundException {
    	
    	ArrayList<Prekid_Rada> prekidiRada = new ArrayList<Prekid_Rada>();
    	
    	connect();
    	preparedStatement = con.prepareStatement("SELECT prekid_rada.*, bolovanje.file, zaposleni.radno_mesto, korisnik.name, korisnik.last_name, korisnik.broj_telefona \n"
    			+ "FROM `prekid_rada` JOIN zaposleni ON prekid_rada.zaposleni_id = zaposleni.korisnik_id \n"
    			+ "join korisnik on prekid_rada.zaposleni_id = korisnik.id \n"
    			+ "LEFT JOIN bolovanje on prekid_rada.id = bolovanje.prekid_rada_id \n"
    			+ "WHERE stanje = ?;");
    	preparedStatement.setString(1, ps.toString());
    	resultSet = preparedStatement.executeQuery();
    	
    	while(resultSet.next()) {
    		
    		String tip_prekida = resultSet.getString("tip_prekida");
    		
    		Prekid_Rada pr = null;
    		
    		if(tip_prekida.equalsIgnoreCase("bolovanje")) {
    			pr = new Bolovanje();
    			((Bolovanje)pr).setFilePath(resultSet.getString("file"));
    		}
    		else if(tip_prekida.equalsIgnoreCase("godisnji"))
    			pr = new Godisnji_Odmor();
    		else if(tip_prekida.equalsIgnoreCase("neplanirani"))
    			pr = new Prekid_Rada();
    		else 
    			throw new IllegalStateException();
    		
    		pr.setId(resultSet.getInt("id"));
    		pr.setDatum_od(resultSet.getObject(1,LocalDate.class));
    		pr.setDatum_do(resultSet.getObject(2,LocalDate.class));
    		
    		String radno_mesto = resultSet.getString(8);
    		
    		Zaposleni z = null;
    		
    		if(radno_mesto.equalsIgnoreCase("tehnicar")) {
    			z = new Tehnicar();
    		}
    		else if(radno_mesto.equalsIgnoreCase("sekretarica")) {
    			z = new Sekretarica();
    		}
    		else 
    			throw new IllegalStateException();
    		
    		z.setId(resultSet.getInt(4));
    		z.setIme(resultSet.getString("name"));
    		z.setPrezime(resultSet.getString("last_name"));
    		z.setBroj_telefona(resultSet.getString("broj_telefona"));
    		
    		pr.setZaposleni(z);
    		
    		prekidiRada.add(pr);
    	}
    	
    	close();
    	return prekidiRada;
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