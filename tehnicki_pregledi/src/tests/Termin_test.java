package tests;

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
import exceptions.invalidAppointmentExcepiton;
import model.Motorno_Vozilo;
import model.Prekid_Rada;
import model.RadnoMestoZaposlenog;
import model.Radno_Vreme;
import model.Tehnicar;
import model.Termin;
import model.TerminState;
import model.Vlasnik;
import model.Vozilo;
import model.VoziloTip;
import model.Zaposleni;
import service.PrekidRadaService;
import service.RadnoVremeService;
import service.SmenaService;
import service.TerminService;
import service.ZaposleniService;

public class Termin_test {
	
	private static TerminDAO tDAO = new TerminDAO();
	private static Radno_Vreme_DAO rvDAO= new Radno_Vreme_DAO();
	private static SmenaDAO sDAO = new SmenaDAO();
	private static TerminService ts = new TerminService();
	private static ZaposleniDAO zDAO = new ZaposleniDAO();
	private static ZaposleniService zService = new ZaposleniService();
	private static RadnoVremeService rvService = new RadnoVremeService();
	private static SmenaService smService = new SmenaService();
	private static PrekidRadaService prService = new PrekidRadaService();
	
	
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
	
	public static void getSlobTerm(LocalDate date, Vozilo v) throws ClassNotFoundException, VoziloTipException, SQLException, UserCreateException, RegDateException, invalidAppointmentExcepiton {
		
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
		
		
		
		
		
		Vlasnik v = new Vlasnik();
		v.setIme("Lazar");
		v.setPrezime("Lazic");
		v.setBroj_telefona("381 66 556677");
		
		
		try {
			ArrayList<Termin> lista = tDAO.readTerminiByVlanik(v);
			for (Termin zaposleni : lista) {
				System.out.println(zaposleni);
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
