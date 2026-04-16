package service;


import model.Bolovanje;
import model.Godisnji_Odmor;
import model.PrekidRadaState;
import model.Prekid_Rada;
import model.Zaposleni;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import dao.Prekid_Rada_DAO;
import exceptions.InvalidFileException;
import exceptions.InvalidGORequestException;

/**
 * 
 */
public class PrekidRadaService {
	
	Prekid_Rada_DAO pr_DAO = new Prekid_Rada_DAO();
	
	
    public void validateDocument(String pf) throws InvalidFileException {
    	
    	Path path = Paths.get(pf);
    	
	    int lastIndexOfDot = pf.lastIndexOf('.');
	        
	    if (lastIndexOfDot == -1 || lastIndexOfDot == 0) {
	    	 throw new InvalidFileException("Nema ekstenzije");
	    }
	    	
	    String extension = pf.substring(lastIndexOfDot + 1);  
    	
	    if(!extension.equals("pdf") && !extension.equals("docx")) {
	    	throw new InvalidFileException("Fajl mora biti ili pdf ili docx");
	    }
	    
	    if(!Files.exists(path) || !Files.isRegularFile(path) || !Files.isReadable(path)) {
	    	throw new InvalidFileException("Fajl nije u redu");
	    }
	    
	    System.out.println("Validation succeded");
    }

    /**
     * @param z 
     * @param pf
     * @throws IOException 
     * @throws InvalidFileException 
     */
    public void addBolovanjeRequest(Zaposleni z, String pf) throws IOException, InvalidFileException {
    	StringBuilder file_path_server = new StringBuilder ();
    	
        validateDocument(pf);
        postRequestDoc(pf, z, file_path_server);
        
        Bolovanje b = new Bolovanje(z, null, null, PrekidRadaState.ZAHTEVA_SE,file_path_server.toString());
        
        pr_DAO.createBolovanjeRequest(b);
         
    }

    /**
     * @throws IOException 
     * 
     */
    public void postRequestDoc(String pf, Zaposleni z, StringBuilder fps) throws IOException {
    	String file_name = z.getIme()+"_"+z.getPrezime()+"_"+LocalDate.now();
    	String extension;
    	
    	int lastIndexOfDot = pf.lastIndexOf('.');
	    	
	    extension = pf.substring(lastIndexOfDot);
	    String targetFile = file_name + extension;
    	
        Path target_path = Paths.get("C:\\Users\\pc\\Desktop\\server\\bolovanja\\"+targetFile);
    	Path source_path = Paths.get(pf);
    	
    	Files.createDirectories(target_path.getParent().getParent());
    	Files.createDirectories(target_path.getParent());
    	
    	Files.copy(source_path, target_path, StandardCopyOption.REPLACE_EXISTING);
    	
    	System.out.println("uspesno kopiran");
    	
    	fps.append("C:\\Users\\pc\\Desktop\\bolovanja\\"+targetFile);
    	
    }

    /**
     * @param z 
     * @param od 
     * @param do
     * @throws InvalidGORequestException 
     */
    public void addGodisnjiRequest(Zaposleni z, LocalDate datum_od, LocalDate datum_do) throws InvalidGORequestException {
        validateGodRequest(z, datum_od, datum_do);
        pr_DAO.createOdmorRequest(z, datum_od, datum_do);
    }

    /**
     * @param z 
     * @param od 
     * @param do
     * @throws InvalidGORequestException 
     */
    private void validateGodRequest(Zaposleni z, LocalDate datum_od, LocalDate datum_do) throws InvalidGORequestException {
        int days_between = (int)ChronoUnit.DAYS.between(datum_od, datum_do);
        int days_until = (int)ChronoUnit.DAYS.between(LocalDate.now(), datum_od);
        System.out.println(days_between+"\n"+days_until);
        if(days_between>z.getBroj_dana_godisnjeg())
        	throw new InvalidGORequestException("Nemate dovoljno dana");
        if(days_until<15)
        	throw new InvalidGORequestException("Morate zakazati 15 dana unapred");
    }

    /**
     * @return
     */
    public ArrayList<Prekid_Rada> getPrekidiRadaZahtevi() {
        // TODO implement here
        return null;
    }

    /**
     * @param bolReq 
     * @param odmReq 
     * @return
     */
    public ArrayList<Prekid_Rada> combinePrekidiRada(ArrayList<Bolovanje> bolReq, ArrayList<Godisnji_Odmor> odmReq) {
        // TODO implement here
        return null;
    }

    /**
     * @param accept
     */
    public void confirmPrekidRada(boolean accept) {
        // TODO implement here
    }

    /**
     * @param p
     */
    public void addPrekidRada(Prekid_Rada p) {
        // TODO implement here
    }

}