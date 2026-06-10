package tests;

import java.util.ArrayList;

import javax.swing.text.StringContent;

import controller.GostController;
import model.Korisnik;
import model.KorisnikTip;
import model.Termin;
import model.Vlasnik;

public class NalogTests {
	
	private static GostController gContr = new GostController();
		
	public static void main(String[] args) {	
			
			Korisnik k = gContr.getNalog(KorisnikTip.ADMIN, "stefan.tutunovic@gmail.com", "0d9d9edd9d");
			
			System.out.println(k.toString());
			
	}

}
