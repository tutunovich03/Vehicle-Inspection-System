package testbench;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import controller.VlasnikController;
import dao.Radno_Vreme_DAO;
import dao.SmenaDAO;
import dao.TerminDAO;
import dao.ZaposleniDAO;
import exceptions.InvalidTermStateException;
import exceptions.RegDateException;
import exceptions.TerminZauzetException;
import exceptions.UserCreateException;
import exceptions.VoziloTipException;
import model.Motorno_Vozilo;
import model.RadnoMestoZaposlenog;
import model.Radno_Vreme;
import model.Tehnicar;
import model.Termin;
import model.TerminState;
import model.Vlasnik;
import model.Vozilo;
import model.VoziloTip;
import model.Zaposleni;
import service.RadnoVremeService;
import service.SmenaService;
import service.TerminService;
import service.ZaposleniService;

public class Termin_tb {
	
	private static TerminDAO tDAO = new TerminDAO();
	private static Radno_Vreme_DAO rvDAO= new Radno_Vreme_DAO();
	private static SmenaDAO sDAO = new SmenaDAO();
	private static TerminService ts = new TerminService();
	private static ZaposleniDAO zDAO = new ZaposleniDAO();
	private static ZaposleniService zService = new ZaposleniService();
	private static RadnoVremeService rvService = new RadnoVremeService();
	private static SmenaService smService = new SmenaService();
	
	
	public static void setState() throws ClassNotFoundException, SQLException {
		Termin t = new Termin(TerminState.REZERVISAN, null, null, null, null);
		try {
			ts.setTerminState(t, TerminState.U_TOKU);
		} catch (InvalidTermStateException e) {
			System.out.println("Invalid input");
		}
	}
	
	public static void readTerminiDatum(LocalDate datum) {
		ArrayList<Termin> lista = new ArrayList<Termin>();
		try {
			lista = tDAO.readTermini(datum);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Termin termin : lista) {
			System.out.println(termin);
		}
		
	}
	
	public static void getSlobTerm(LocalDate date, Vozilo v) throws ClassNotFoundException, VoziloTipException, SQLException, UserCreateException {
		
		ArrayList<LocalTime> list = ts.getSlobodniTermini(date, v);
		
		System.out.println("Slobodni termini:");
		for (LocalTime el : list) {
			System.out.println(el.toString());
			
		}
		System.out.println("end");
	}
	
	private static VlasnikController vCont = new VlasnikController();
	public static void createTerm(LocalTime vreme, LocalDate date, Vozilo v, Vlasnik vl) throws ClassNotFoundException, TerminZauzetException, VoziloTipException, RegDateException, SQLException, UserCreateException {
		vCont.addTerminVozilo(vreme, date, v, vl);
	}
	
	public static void main(String[] args) {	
		
		Vozilo v = new Motorno_Vozilo();
		v.setId(4);
		LocalDate datum = LocalDate.of(2026, 4, 20);
		Vlasnik vl = new Vlasnik();
		vl.setId(15);
		Tehnicar teh = new Tehnicar();
		teh.setId(5);
		Termin term = new Termin();
		term.setStanje(TerminState.REZERVISAN);
		term.setDatum_Termina(LocalDate.now());
		term.setVozilo(v);
		
		
		
		
		try {
			smService.setBrojSmena(2);
			System.out.println("end");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
