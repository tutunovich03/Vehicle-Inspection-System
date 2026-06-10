package service;

import model.*;

import java.io.*;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import dao.Prekid_Rada_DAO;
import dao.Radno_Vreme_DAO;
import dao.SmenaDAO;
import dao.SystemDAO;
import dao.TerminDAO;
import dao.VoziloDAO;
import dao.ZaposleniDAO;
import exceptions.InvalidTermStateException;
import exceptions.RegDateException;
import exceptions.TerminZauzetException;
import exceptions.UserCreateException;
import exceptions.VoziloTipException;
import exceptions.invalidAppointmentExcepiton;


/**
 * 
 */
public class TerminService {

    /**
     * Default constructor
     */
	
	TerminDAO terminDAO = new TerminDAO();
	VoziloDAO voziloDAO = new VoziloDAO();
	Prekid_Rada_DAO prDAO= new Prekid_Rada_DAO();
	ZaposleniDAO zDAO = new ZaposleniDAO();
	SmenaDAO sDAO = new SmenaDAO();
	Radno_Vreme_DAO rvDAO = new Radno_Vreme_DAO();
	VoziloService vozServ = new VoziloService();
	
    public TerminService() {
    }

    /**
     * @param t 
     * @param state
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    
    public ArrayList<Termin> getTerminiDanas(Tehnicar t) throws ClassNotFoundException, SQLException { //-------
    	return terminDAO.readTerminiDanas(t);
    }
    
    
    public void setTerminState(Termin t, TerminState state) throws InvalidTermStateException, ClassNotFoundException, SQLException { //-------
    	validateTermState(t, state);
    	t.setStanje(state);
    	System.out.println(t.getStanje());
    	terminDAO.updateTerminState(t);
    }
    

    /**
     * @param t 
     * @param state
     * @throws InvalidTermStateException 
     */
    public void validateTermState(Termin t, TerminState state) throws InvalidTermStateException {
        if(state == TerminState.NEODRZAN) {
        	if(t.getStanje() != TerminState.REZERVISAN) throw new InvalidTermStateException();
        }
        if(state == TerminState.U_TOKU) {
        	if(t.getStanje() != TerminState.REZERVISAN && t.getStanje() != TerminState.PREKINUT) throw new InvalidTermStateException();
        }
        if(state == TerminState.PREKINUT || state == TerminState.NEISPRAVAN || state == TerminState.ISPRAVAN) {
        	if(t.getStanje() != TerminState.U_TOKU) throw new InvalidTermStateException();
        }
        if(state == TerminState.NEISPRAVAN_POPUNJEN) {
        	if(t.getStanje() != TerminState.NEISPRAVAN) throw new InvalidTermStateException();
        }
        if(state == TerminState.ZAVRSEN) {
        	if(t.getStanje() != TerminState.ISPRAVAN) throw new InvalidTermStateException();
        }
    }

    /**
     * @param t 
     * @param rez
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public ArrayList<Termin> getTermini(Vlasnik v, TerminState ts) throws ClassNotFoundException, SQLException{ //-----
    	return terminDAO.readTerminiVlasnik(v,ts);
    }
    
    public void deleteTermin(Termin t) throws ClassNotFoundException, SQLException { //----
    	terminDAO.removeTermin(t);
    	
    	ArrayList<Termin> termini = getTermini(t.getVozilo().getVlasnik(), null);
    	for (Termin termin : termini) {
			if(t.getVozilo().equals(termin.getVozilo())) {
				return;
			}
		}
    	
    	voziloDAO.removeVozilo(t.getVozilo());
    }
    
    
    
    
    public void removeTermin(Termin t) throws ClassNotFoundException, SQLException {
    	terminDAO.removeTermin(t);
    }

    /**
     * @param vreme 
     * @param d 
     * @param v
     * @throws TerminZauzetException 
     * @throws VoziloTipException 
     * @throws RegDateException 
     * @throws invalidAppointmentExcepiton 
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    
    public void verifyTermin(Vozilo v, LocalDate date)  throws VoziloTipException, RegDateException, invalidAppointmentExcepiton {
    	if(v.getDatum_isteka_registracije().isAfter(date.plusDays(30)))
    		throw new RegDateException();
    	if(/*date.isBefore(LocalDate.now().plusDays(3)) ||*/ date.isAfter(LocalDate.now().plusDays(15)))
    			throw new invalidAppointmentExcepiton();
    }
    
    
    public Tehnicar dodeliTehnicara(LocalTime vreme, ArrayList<Termin> termini_slobodnih, ArrayList<Radno_Vreme_Zaposlenog> rv_slobodnih) throws VoziloTipException {
    	
    	Tehnicar izabrani = null;
    	int max_durration = 0;
    	
    	for (Radno_Vreme_Zaposlenog rvs : rv_slobodnih) {
			Tehnicar teh = (Tehnicar)rvs.getZaposleni();
			ArrayList<Termin> term_teh = new ArrayList<Termin>();
			
			for (Termin term : termini_slobodnih) {
				if(term.getTehnicar().equals(teh)) {
					term_teh.add(term);
				}
			}
			
			LocalTime pre = rvs.getVreme_od();
			LocalTime posle = rvs.getVreme_do();
			
			for (Termin term : term_teh) {
				
				LocalTime pocetak_term = term.getVreme_pocetka();
				LocalTime kraj_term = term.getVreme_pocetka().plusMinutes(term.getVozilo().trajanje_tehnickog_min());
				
				if(kraj_term.isBefore(vreme) && kraj_term.isAfter(pre)) {
					pre = kraj_term;
				}
				
				if(pocetak_term.isAfter(vreme) && pocetak_term.isBefore(posle)) {
					posle = pocetak_term;
				}
				
			}
			
			int duration = (int) Duration.between(pre, posle).toMinutes();
			
			if(duration > max_durration) {
				max_durration = duration;
				izabrani = teh;
			}
			
		}
    	return izabrani;
    }
    
    public void addTermin(LocalTime vreme, LocalDate date, Vozilo vozilo, Vlasnik vl) throws TerminZauzetException, VoziloTipException, RegDateException, ClassNotFoundException, SQLException, UserCreateException, invalidAppointmentExcepiton {  //---------
    	
    	verifyTermin(vozilo, date);
    	int trajanje_pauze = new SystemDAO().readTrajanjePauze();
    	ArrayList<Termin> zakazani_termini = terminDAO.readTermini(date);
    	ArrayList<Zaposleni> tehnicari =  zDAO.readZapsoleniList(RadnoMestoZaposlenog.TEHNICAR);
    	for (Zaposleni zaposleni : tehnicari) {
    		if(zaposleni instanceof Tehnicar)
				for (Termin term : zakazani_termini) {
					if(term.getTehnicar().getId() == zaposleni.getId())
						term.setTehnicar((Tehnicar)zaposleni);
				}
		}
    	Radno_Vreme rv_datuma = rvDAO.readRadnoVreme(date);
    	ArrayList<Radno_Vreme_Zaposlenog> rv_zaposlenih = radnaVremenaTehnicara(tehnicari, rv_datuma, trajanje_pauze);
    	
    	for (Radno_Vreme_Zaposlenog rvz : rv_zaposlenih) {
			System.out.println(rvz);
		}
    	
    	ArrayList<Tehnicar> slobodni_tehnicari_lista = AvaliableTehnicarZaVreme(vreme, rv_zaposlenih, zakazani_termini, vozilo, trajanje_pauze);
    	
    	System.out.println("Slobodni tehnicari");
    	for (Tehnicar tehnicar : slobodni_tehnicari_lista) {
			System.out.println(tehnicar);
		}
    	System.out.println("end");
    	
    	if(slobodni_tehnicari_lista.isEmpty())
    		throw new TerminZauzetException();
    	
    	vozServ.addVozilo(vozilo,vl);
    	
    	ArrayList<Termin> termini_slobodnih = new ArrayList<Termin>();
    	ArrayList<Radno_Vreme_Zaposlenog> radno_vreme_slobodnih = new ArrayList<Radno_Vreme_Zaposlenog>();
    	
    	for(Tehnicar teh : slobodni_tehnicari_lista) {
    		for(Termin term : zakazani_termini) {
    			if(term.getTehnicar().equals(teh)) {
    				termini_slobodnih.add(term);
    			}
    		}
    	}
    	
    	for(Radno_Vreme_Zaposlenog rvz : rv_zaposlenih) {
    		for(Tehnicar teh : slobodni_tehnicari_lista) {
    			if(rvz.getZaposleni().equals(teh))
    				radno_vreme_slobodnih.add(rvz);
    		}
    	}
    	
    	System.out.println("radna v zap:");
    	for (Radno_Vreme_Zaposlenog rv : radno_vreme_slobodnih) {
			System.out.println(rv);
		}
    	System.out.println("end");
    	
    	Tehnicar izabrani_tehnicar = dodeliTehnicara(vreme, termini_slobodnih, radno_vreme_slobodnih);
    	
    	System.out.println(izabrani_tehnicar.getId());
    	
    	Termin newTermin = new Termin(TerminState.REZERVISAN, izabrani_tehnicar, vozilo, vreme, date);
    	
    	System.out.print("rezervisani termin: ");
    	System.out.println(newTermin);
    	
    	terminDAO.createTermin(newTermin);
    	
    }
    
 
    public ArrayList<LocalTime> getSlobodniTermini(LocalDate date, Vozilo vozilo) throws VoziloTipException, ClassNotFoundException, SQLException, UserCreateException, RegDateException, invalidAppointmentExcepiton { // ------------
    	
    	verifyTermin(vozilo, date);
    	int trajanje_pauze = new SystemDAO().readTrajanjePauze();
    	ArrayList<Termin> zakazani_termini = terminDAO.readTermini(date);
    	ArrayList<Zaposleni> tehnicari =  zDAO.readZapsoleniList(RadnoMestoZaposlenog.TEHNICAR);
    	for (Zaposleni zaposleni : tehnicari) {
    		if(zaposleni instanceof Tehnicar)
				for (Termin term : zakazani_termini) {
					if(term.getTehnicar().getId() == zaposleni.getId())
						term.setTehnicar((Tehnicar)zaposleni);
				}
		}
    	Radno_Vreme rv_datuma = rvDAO.readRadnoVreme(date);
    	ArrayList<Radno_Vreme_Zaposlenog> rv_zaposlenih = radnaVremenaTehnicara(tehnicari, rv_datuma, trajanje_pauze);
    	
    	for (Radno_Vreme_Zaposlenog rvz : rv_zaposlenih) {
			System.out.println(rvz);
		}
    	
    	ArrayList<LocalTime> slobodniTermini = new ArrayList<LocalTime>();
    	
    	for(LocalTime time = rv_datuma.getVreme_od(); time != rv_datuma.getVreme_do(); time = time.plusMinutes(15)) {
    		if(!AvaliableTehnicarZaVreme(time, rv_zaposlenih, zakazani_termini, vozilo, trajanje_pauze).isEmpty()) {
    			slobodniTermini.add(time);
    		}
    	}
    	
    	
    	
        return slobodniTermini;
    }
    
    public ArrayList<Tehnicar> AvaliableTehnicarZaVreme(LocalTime time, ArrayList<Radno_Vreme_Zaposlenog> rvz, ArrayList<Termin> zakazTerm, Vozilo v, int trajanje_pauze) throws VoziloTipException {
    	int trajanje = v.trajanje_tehnickog_min();
    	
    	ArrayList<Zaposleni> aktivniTehnicari = new ArrayList<Zaposleni>();
    	
    	for (Radno_Vreme_Zaposlenog rvZap : rvz) {
			if(!rvZap.getVreme_od().isAfter(time) && !rvZap.getVreme_do().isBefore(time.plusMinutes(trajanje)) && rvZap.getZaposleni().isAktivan()) {
				if(rvZap.getZaposleni().getVreme_pauze().isAfter(time.plusMinutes(trajanje)) || rvZap.getZaposleni().getVreme_pauze().plusMinutes(trajanje_pauze).isBefore(time)) {
					aktivniTehnicari.add(rvZap.getZaposleni());
				}
			}
		}
    	
    	ArrayList<Termin> termini_aktivnih = new ArrayList<Termin>();
    	
    	for (Zaposleni z : aktivniTehnicari) {
			for (Termin termin : zakazTerm) {
				if(termin.getTehnicar().getId()==z.getId())
					termini_aktivnih.add(termin);
			}
		}
    	
    	/*System.out.println("Termini aktivnih: ");
    	for (Termin termin : termini_aktivnih) {
			System.out.println(termin);
		}
    	System.out.println("end");*/
    	
    	for (Termin termin : termini_aktivnih) { 
    		LocalTime pocetak_zakaz = termin.getVreme_pocetka();
    		LocalTime kraj_zakaz = termin.getVreme_pocetka().plusMinutes(termin.getVozilo().trajanje_tehnickog_min());
    		LocalTime pocetak_novog = time;
    		LocalTime kraj_novog = time.plusMinutes(trajanje);
    		
			if(pocetak_zakaz.isBefore(kraj_novog) && kraj_zakaz.isAfter(pocetak_novog)) {
				termin.getTehnicar().setTrenutno_zauzet(true);
			}
		}
    	
    	ArrayList<Tehnicar> aktivniZaVreme = new ArrayList<Tehnicar>();
    	
    	for (Zaposleni teh : aktivniTehnicari) {	
    		if(teh instanceof Tehnicar) {
    			if(!((Tehnicar) teh).isTrenutno_zauzet())
    				aktivniZaVreme.add((Tehnicar)teh);
    		}
		}
    	
    	for (Zaposleni zaposleni : aktivniTehnicari) {
			if(zaposleni instanceof Tehnicar) {
				((Tehnicar) zaposleni).setTrenutno_zauzet(false);
			}
		}
    	
    	return aktivniZaVreme;
    }
    
    
    public ArrayList<Radno_Vreme_Zaposlenog> radnaVremenaTehnicara(ArrayList<Zaposleni> tehnicari, Radno_Vreme rv, int trajanje_pauze) throws ClassNotFoundException, SQLException{
    	ArrayList<Radno_Vreme_Zaposlenog> rvz_list = new ArrayList<Radno_Vreme_Zaposlenog>();
    	for (Zaposleni teh : tehnicari) {
			rvz_list.add(new Radno_Vreme_Zaposlenog(rv.getDatum(), LocalTime.of(0,0), LocalTime.of(0, 0), teh));
		}
    	
    	
    	int broj_smena = sDAO.readBrojSmena();
    	LocalDate ref_date = LocalDate.of(2026,3,9); //referentni ponedeljak
    	int days_btw = (int) ChronoUnit.DAYS.between(ref_date, rv.getDatum());
    	
    	
    	int broj_minuta = (int)Duration.between(rv.getVreme_od(), rv.getVreme_do()).toMinutes();
    	int mod = (days_btw / 7) % broj_smena;
    	//System.out.println("days btw: " + days_btw + "mod: " + mod);
    	
    	setZaposleniAsNeaktivan(tehnicari, rv); // oznacava neaktivnim zaposlene koji taj dan ne rade
    	
    	ArrayList<Zaposleni> aktivniTehnicari = new ArrayList<Zaposleni>();
    	
    	for (Zaposleni tehnicar : tehnicari) {
			if(tehnicar.isAktivan())
				aktivniTehnicari.add(tehnicar);
		}
    	
    	
    	
    	for(int i = 0; i<broj_smena; i++) { //dodela radnog vremena svim radnicima u zavisnosti od smene
    		int m = mod + i;
    		
    		int b;
    		b = m;
    		
    		if(m>=broj_smena) {
    			b = m - broj_smena;
    		}
    		
    		for (Radno_Vreme_Zaposlenog rvz : rvz_list) {
				if(rvz.getZaposleni().getGrupa() == i + 1 && rvz.getZaposleni().isAktivan() == true) {
					rvz.setVreme_od(rv.getVreme_od().plusMinutes(b*broj_minuta/broj_smena));
					rvz.setVreme_do(rv.getVreme_od().plusMinutes((b+1)*broj_minuta/broj_smena));
					setVremePauzeZaposlenog(tehnicari, rvz, trajanje_pauze);

				}
			}
    		
    	}

    	return rvz_list;
    } 
    
    
    
    
    public void setZaposleniAsNeaktivan(ArrayList<Zaposleni> zaposleni_list, Radno_Vreme rv) throws ClassNotFoundException, SQLException {
    	ArrayList<Prekid_Rada> prekidi_rada = prDAO.readPrekidRadaUToku(rv.getDatum());
    	
    	for (Prekid_Rada pr : prekidi_rada) {
			
			for (Zaposleni z : zaposleni_list) {
				if(z.getId() == pr.getZaposleni_id())
					z.setAktivan(false);
			}
			
		}
    	
    	/*if(rv.getVreme_od() == LocalTime.of(0, 0) && rv.getVreme_do() == LocalTime.of(0, 0)) {
    		for (Zaposleni z : zaposleni_list) {
				z.setAktivan(false);
			}
    		
    	}*/
    	
    	
    }
    
    
    public void setVremePauzeZaposlenog(ArrayList<Zaposleni> zaposleni, Radno_Vreme_Zaposlenog rvz, int trajanje_pauze) {
    	Zaposleni teh = rvz.getZaposleni();
		int br_tehnicara_u_grupi = 0;
		
		for (Zaposleni t : zaposleni) { 
			if(t.getGrupa() == teh.getGrupa()) { 
				br_tehnicara_u_grupi++;
			}
		}
		
		Duration trajanjeSmene = Duration.between(rvz.getVreme_od(), rvz.getVreme_do());
		
		int neparan = 0;
		if(br_tehnicara_u_grupi % 2 != 0) {
			neparan = 1;
		}
		 
		LocalTime ref_time = rvz.getVreme_od().plusMinutes(trajanjeSmene.toMinutes()/2-neparan*trajanje_pauze/2);
		System.out.println(ref_time);
		
		int red_pauze = teh.getRedPauze();
		
		
		int px = -br_tehnicara_u_grupi/2;
		int x = px + red_pauze - 1;
		
		LocalTime vp = ref_time.plusMinutes(x*trajanje_pauze);
		
		teh.setVreme_pauze(vp);
    }
    
    public ArrayList<Termin> getPregledanaVozilaTerm() throws ClassNotFoundException, SQLException {
    	return terminDAO.readPregledanaVozTerm();
    }
       

    /**
     * @param v 
     * @return
     */
    public ArrayList<Termin> getTerminiByVehicle(Vozilo v) {
        // TODO implement here
        return null;
    }

}