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

import exceptions.VoziloTipException;

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
    
    public ArrayList<Termin> readTerminiByVlanik(Vlasnik v) throws SQLException, ClassNotFoundException{
    	connect();
    	
    	ArrayList<Termin> termini = new ArrayList<Termin>();
    	
    	preparedStatement = con.prepareStatement("SELECT termin.vreme_pocetka, termin.datum_termina, vozilo.tip, tehnicar_id FROM termin JOIN vozilo ON vozilo.vozilo_id = termin.vozilo_id JOIN korisnik on vozilo.vlasnik_id = korisnik.id WHERE korisnik.name = ? AND korisnik.last_name = ? AND korisnik.broj_telefona = ? AND termin.stanje = \"rezervisan\";");
    	preparedStatement.setString(1, v.getIme());
    	preparedStatement.setString(2, v.getPrezime());
    	preparedStatement.setString(3, v.getBroj_telefona());
    	resultSet = preparedStatement.executeQuery();
    	
    	while(resultSet.next()) {
    		LocalTime vreme = resultSet.getObject(1, LocalTime.class);
    		LocalDate datum = resultSet.getObject(2, LocalDate.class);
    		String tip_str = resultSet.getString(3);
    		int teh_id = resultSet.getInt(4);
    		
    		Tehnicar tehnicar = new Tehnicar();
    		tehnicar.setId(teh_id);
    		
    		Vozilo voz = Vozilo.createVozilo(VoziloTip.valueOf(tip_str));
    		
    		Termin term = new Termin();
    		term.setDatum_Termina(datum);
    		term.setStanje(TerminState.REZERVISAN);
    		term.setTehnicar(tehnicar);
    		term.setVozilo(voz);
    		term.setVreme_pocetka(vreme);
    		
    		termini.add(term);
    		
    	}
    	close();
    	
    	return termini;
    
    }
    
    public ArrayList<Termin> readTerminiDanas(Tehnicar t) throws ClassNotFoundException, SQLException {
        ArrayList<Termin> lista = new ArrayList<Termin>();
        connect();
        statement = con.createStatement();
        resultSet = statement.executeQuery("SELECT termin.*, vozilo.tip FROM `termin` JOIN vozilo on termin.vozilo_id = vozilo.vozilo_id WHERE termin.datum_termina = CURRENT_DATE() AND tehnicar_id = " +t.getId()+ ";");
        
        while(resultSet.next()) {
        	String stanje_str = resultSet.getString(1);
        	int voz_id = resultSet.getInt(3);
        	LocalTime vreme_poc = resultSet.getObject(4,LocalTime.class);
        	String tip_voz_str = resultSet.getString(7);
        	System.out.println(tip_voz_str);
        	
        	
        	VoziloTip tip_voz = VoziloTip.valueOf(tip_voz_str);
        	TerminState terminState = TerminState.valueOf(stanje_str.toUpperCase());
        	
        	Vozilo v = Vozilo.createVozilo(tip_voz);
        	v.setId(voz_id);
        	
        	Termin term = new Termin();
        	term.setVozilo(v);
        	term.setVreme_pocetka(vreme_poc);
        	term.setStanje(terminState);
        	
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
    		preparedStatement = con.prepareStatement("SELECT `datum_termina`, vozilo.tip, vozilo.model, vozilo.vozilo_id, `vreme_pocetka`, `tehnicar_id` FROM `termin` JOIN vozilo ON termin.vozilo_id = vozilo.vozilo_id WHERE vozilo.vlasnik_id = ? AND termin.stanje = ?;");
    		preparedStatement.setString(2, ts.toString());
    	}
    	else {
    		preparedStatement = con.prepareStatement("SELECT `datum_termina`, vozilo.tip, vozilo.model, vozilo.vozilo_id, `vreme_pocetka`, `tehnicar_id` FROM `termin` JOIN vozilo ON termin.vozilo_id = vozilo.vozilo_id WHERE vozilo.vlasnik_id = ?;");
    	}
    	preparedStatement.setInt(1, v.getId());
    	
    	resultSet = preparedStatement.executeQuery();
    	
    	while(resultSet.next()) {
    		LocalDate datum = resultSet.getObject(1, LocalDate.class);
    		LocalTime vreme = resultSet.getObject(5, LocalTime.class);
    		String tip_str = resultSet.getString(2);
    		String model = resultSet.getString(3);
    		int voz_id = resultSet.getInt(4);
    		int teh_id = resultSet.getInt(6);
    		
    		VoziloTip tip = VoziloTip.valueOf(tip_str.toUpperCase());
    		
    		Vozilo vozilo = Vozilo.createVozilo(tip);
    		vozilo.setId(voz_id);
    		vozilo.setModel(model);
    		vozilo.setVlasnik(v);
    		
    		Termin termin = new Termin();
    		termin.setDatum_Termina(datum);
    		termin.setStanje(ts);
    		termin.setVozilo(vozilo);
    		termin.setVreme_pocetka(vreme);
    		
    		Tehnicar teh = new Tehnicar();
    		teh.setId(teh_id);
    		
    		termin.setTehnicar(teh);
    		
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
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void removeTermin(Termin t) throws ClassNotFoundException, SQLException {
        connect();
        preparedStatement = con.prepareStatement("DELETE FROM `termin` WHERE `stanje`= \"rezervisan\" AND `tehnicar_id` = ? AND `vozilo_id` = ? AND `vreme_pocetka` = ? AND `datum_termina` = ?;");
        preparedStatement.setInt(1,t.getTehnicar().getId());
        preparedStatement.setInt(2, t.getVozilo().getId());
        preparedStatement.setObject(3, t.getVreme_pocetka());
        preparedStatement.setObject(4, t.getDatum_Termina());
        preparedStatement.executeUpdate();
        
        close();
        
    }

    /**
     * @param v 
     * @return
     */
    public ArrayList<Termin> readTerminiByVehicle(Vozilo v) {
        // TODO implement here
        return null;
    }
    
    public ArrayList<Termin> readPregledanaVozTerm() throws ClassNotFoundException, SQLException{
    	
    	ArrayList<Termin> termini = new ArrayList<Termin>();
    	connect();
    	statement = con.createStatement();
    	resultSet = statement.executeQuery("SELECT termin.stanje, termin.vreme_pocetka, termin.datum_termina, vozilo.*, termin.tehnicar_id FROM `termin` JOIN vozilo ON termin.vozilo_id = vozilo.vozilo_id WHERE stanje = \"ispravan\" OR stanje = \"neispravan\";");
    	
    	while(resultSet.next()) {
    		String stanje_str = resultSet.getString(1);
    		LocalTime vreme_pocetka = resultSet.getObject(2, LocalTime.class);
    		LocalDate datum_termina = resultSet.getObject(3, LocalDate.class);
    		String tip_str = resultSet.getString(4);
    		String reg_oznaka = resultSet.getString(5);
    		String broj_sasije = resultSet.getString(6);
    		LocalDate istek_reg = resultSet.getObject(7, LocalDate.class);
    		int vlasnik_id = resultSet.getInt(8);
    		int vozilo_id = resultSet.getInt(9);
    		String model = resultSet.getString(10);
    		int cena = resultSet.getInt(11);
    		int teh_id = resultSet.getInt(12);
    		
    		TerminState stanje = TerminState.valueOf(stanje_str);
    		VoziloTip tip = VoziloTip.valueOf(tip_str);
    		
    		Vozilo v = Vozilo.createVozilo(tip);
    		v.setBroj_sasije(broj_sasije);
    		v.setDatum_isteka_registracije(istek_reg);
    		v.setModel(model);
    		v.setId(vozilo_id);
    		v.setRegistarski_broj(reg_oznaka);
    		Vlasnik vl = new Vlasnik();
    		vl.setId(vlasnik_id);
    		v.setVlasnik(vl);
    		v.setCena(cena);
    		
    		Termin t = new Termin();
    		t.setDatum_Termina(datum_termina);
    		t.setStanje(stanje);
    		Tehnicar teh = new Tehnicar();
    		teh.setId(teh_id);
    		t.setTehnicar(teh);
    		t.setVozilo(v);
    		t.setVreme_pocetka(vreme_pocetka);
    		
    		termini.add(t);
    		
    		
    	}
    	close();
    	
    	return termini;
    }
    
    
    public void deleteTermin(Termin t) throws ClassNotFoundException, SQLException {
    	connect();
    	preparedStatement = con.prepareStatement("DELETE FROM `termin` WHERE `datum_termina` = ? AND `vreme_pocetka` = ? `tehnicar_id` = ?");
    	preparedStatement.executeUpdate();
    	close();
    }

}