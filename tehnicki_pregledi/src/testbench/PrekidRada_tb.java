package testbench;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import dao.Prekid_Rada_DAO;
import exceptions.InvalidFileException;
import exceptions.InvalidGORequestException;
import exceptions.UserCreateException;
import model.Prekid_Rada;
import model.Tehnicar;
import model.Zaposleni;
import service.*;

public class PrekidRada_tb {
	
	private static Prekid_Rada_DAO prDAO = new Prekid_Rada_DAO();
	
	public static void zakazivanje_bolovanja_test(Zaposleni z) {	
		PrekidRadaService p = new PrekidRadaService();
		
		String path = "C:\\Users\\pc\\Desktop\\CV\\Stefan Tutunović.docx";
		
		
		
		try {
			p.addBolovanjeRequest(z, path);
		}
		catch(InvalidFileException e) {
			System.out.println(e.getMessage());
		}
		catch(IOException e) {
			System.out.println("greska");
		}
		
	}
	
	public static void zakazivanje_god_odm_test(Zaposleni z) {
		PrekidRadaService p = new PrekidRadaService();
		
		
		
		try {
			p.addGodisnjiRequest(z, LocalDate.of(2026, 3, 29), LocalDate.of(2026, 3, 30));
		}
		catch (InvalidGORequestException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		try {
			/*ArrayList<Prekid_Rada> lista = prDAO.readPrekidRadaUToku(LocalDate.of(2026, 4, 2));
			for (Prekid_Rada prekid_Rada : lista) {
				System.out.println(prekid_Rada.toString());
			}*/
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
