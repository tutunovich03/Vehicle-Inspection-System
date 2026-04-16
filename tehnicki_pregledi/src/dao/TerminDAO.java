package dao;

import model.Tehnicar;
import model.Teretno_Vozilo;
import model.Termin;
import model.TerminState;
import model.Vlasnik;
import model.Nalog;
import model.Korisnik;
import model.Motorno_Vozilo;
import model.Vozilo;
import model.VoziloTip;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * 
 */
public class TerminDAO extends DAO{

    /**
     * Default constructor
     */
    public TerminDAO() {
    }

    /**
     * @param t 
     * @return
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public ArrayList<Termin> readTerminiDanas(Tehnicar t) throws ClassNotFoundException, SQLException {
        ArrayList<Termin> lista = new ArrayList<Termin>();
        connect();
        statement = con.createStatement();
        resultSet = statement.executeQuery("SELECT termin.*, vozilo.tip FROM `termin` JOIN vozilo on termin.vozilo_id = vozilo.vozilo_id WHERE termin.stanje = \"REZERVISAN\" AND termin.datum_termina = CURRENT_DATE() AND tehnicar_id = " +t.getId()+ ";");
        
        while(resultSet.next()) {
        	int voz_id = resultSet.getInt(3);
        	LocalTime vreme_poc = resultSet.getObject(4,LocalTime.class);
        	String tip_voz_str = resultSet.getString(6);
        	
        	VoziloTip tip_voz = VoziloTip.valueOf(tip_voz_str);
        	
        	Vozilo v = Vozilo.createVozilo(tip_voz);
        	v.setId(voz_id);
        	
        	Termin term = new Termin();
        	term.setVozilo(v);
        	term.setVreme_pocetka(vreme_poc);
        	
        	lista.add(term);
        	
        }
        
        close();
        return lista;
    }

    /**
     * @param t 
     * @param state
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void updateTerminState(Termin t) throws ClassNotFoundException, SQLException {
        connect();
        preparedStatement = con.prepareStatement("UPDATE `termin` SET `stanje`=? WHERE `datum_termina` = CURRENT_DATE AND `vozilo_id` = ?;");
        preparedStatement.setString(1, t.getStanje().toString());
        preparedStatement.setInt(2, t.getVozilo().getId());
        preparedStatement.executeUpdate();
        close();
    }

    /**
     * @param datum 
     * @return
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public ArrayList<Termin> readTermini(LocalDate datum) throws SQLException, ClassNotFoundException {
    	ArrayList<Termin> termini = new ArrayList<Termin>();
		connect();
		preparedStatement = con.prepareStatement("SELECT termin.stanje, termin.vreme_pocetka, termin.datum_termina,\n"
				+ "korisnik.id, korisnik.name, korisnik.last_name, \n"
				+ "vozilo.tip, vozilo.reg_oznaka\n"
				+ "FROM `termin`\n"
				+ "JOIN vozilo on termin.vozilo_id = vozilo.vozilo_id\n"
				+ "JOIN korisnik on termin.tehnicar_id = korisnik.id\n"
				+ "WHERE datum_termina = ? AND termin.stanje = 'rezervisan';");
		preparedStatement.setString(1,datum.toString());
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			String term_state_str = resultSet.getString(1);
			LocalTime vreme_pocetka = resultSet.getObject(2,LocalTime.class);
			LocalDate datum_termina = resultSet.getObject(3,LocalDate.class);
			int tehnicar_id = resultSet.getInt(4);
			String ime_teh = resultSet.getString(5);
			String prezime_teh = resultSet.getString(6);
			String vozilo_tip_str = resultSet.getString(7);
			String vozilo_reg = resultSet.getString(8);
			
			
			TerminState term_state = TerminState.valueOf(term_state_str.toUpperCase());
			VoziloTip vozilo_tip = VoziloTip.valueOf(vozilo_tip_str.toUpperCase());
			
			Tehnicar teh = new Tehnicar();
			teh.setId(tehnicar_id);
			teh.setIme(ime_teh);
			teh.setPrezime(prezime_teh);
			
			Vozilo v = Vozilo.createVozilo(vozilo_tip);
			v.setRegistarski_broj(vozilo_reg);
			
			termini.add(new Termin(term_state, teh, v, vreme_pocetka, datum_termina));
		}
		
		close();
		
        return termini;
    }

    /**
     * @param n 
     * @return
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public ArrayList<Termin> readTerminiVlasnik(Vlasnik v, TerminState ts) throws ClassNotFoundException, SQLException { //istorija pregleda, pregled zakazanih
        ArrayList<Termin> terminiVlasnik = new ArrayList<Termin>();
    	connect();
    	if(ts!=null) {
    		preparedStatement = con.prepareStatement("SELECT `datum_termina`, vozilo.tip, vozilo.model, vozilo.vozilo_id FROM `termin` JOIN vozilo ON termin.vozilo_id = vozilo.vozilo_id WHERE vozilo.vlasnik_id = ? AND termin.stanje = ?;");
    		preparedStatement.setString(2, ts.toString());
    	}
    	else {
    		preparedStatement = con.prepareStatement("SELECT `datum_termina`, vozilo.tip, vozilo.model, vozilo.vozilo_id FROM `termin` JOIN vozilo ON termin.vozilo_id = vozilo.vozilo_id WHERE vozilo.vlasnik_id = ?;");
    	}
    	preparedStatement.setInt(1, v.getId());
    	
    	resultSet = preparedStatement.executeQuery();
    	
    	while(resultSet.next()) {
    		LocalDate datum = resultSet.getObject(1, LocalDate.class);
    		String tip_str = resultSet.getString(2);
    		String model = resultSet.getString(3);
    		int voz_id = resultSet.getInt(4);
    		
    		VoziloTip tip = VoziloTip.valueOf(tip_str.toUpperCase());
    		
    		Vozilo vozilo = Vozilo.createVozilo(tip);
    		vozilo.setId(voz_id);
    		vozilo.setModel(model);
    		vozilo.setVlasnik(v);
    		
    		Termin termin = new Termin();
    		termin.setDatum_Termina(datum);
    		termin.setStanje(ts);
    		termin.setVozilo(vozilo);
    		
    		terminiVlasnik.add(termin);
    		
    	}
    	
    	
    			
    	close();
        return terminiVlasnik;
    }

    /**
     * @param t
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void createTermin(Termin t) throws ClassNotFoundException, SQLException {
        connect();
        preparedStatement = con.prepareStatement("INSERT INTO `termin`(`stanje`, `tehnicar_id`, `vozilo_id`, `vreme_pocetka`, `datum_termina`) VALUES (?,?,?,?,?)");
        preparedStatement.setString(1, t.getStanje().toString());
        preparedStatement.setInt(2, t.getTehnicar().getId());
        preparedStatement.setInt(3, t.getVozilo().getId());
        preparedStatement.setObject(4, t.getVreme_pocetka());
        preparedStatement.setObject(5, t.getDatum_Termina());
        
        preparedStatement.executeUpdate();
        close();
        
    }

    /**
     * @param t
     */
    public void removeTermin(Termin t) {
        // TODO implement here
    }

    /**
     * @param v 
     * @return
     */
    public ArrayList<Termin> readTerminiByVehicle(Vozilo v) {
        // TODO implement here
        return null;
    }

}