package service;


import java.io.*;
import java.sql.SQLException;
import java.util.*;

import dao.DAO;
import dao.SmenaDAO;

/**
 * 
 */
public class SmenaService {

    DAO d = new DAO();
    SmenaDAO sDAO = new SmenaDAO();
	
    public SmenaService() {
    }

    /**
     * @param brSmena 
     * @param smene[]
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void setBrojSmena(int brSmena) throws ClassNotFoundException, SQLException {
    	try {
    		brojSmenaValidation(brSmena);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return;
    	}
    	
    	int trenutniBroj = d.countRows("grupa");
    	System.out.println("trenutni broj: "+trenutniBroj);
    	
    	if(brSmena > trenutniBroj) {
    		for(int i = 0; i < brSmena-trenutniBroj; i++) {
    			sDAO.addSmena();
    			System.out.println("raste "+i);
    		}
    	}
    	else {
    		for(int i = 0; i < trenutniBroj - brSmena; i++) {
    			sDAO.deleteSmena();
    			System.out.println("smanjuje se "+i);
    		}
    	}
    	
    }

    /**
     * @throws Exception 
     * 
     */
    public void brojSmenaValidation(int brSmena) throws Exception {
        if(brSmena > 3 || brSmena<1)
        	throw new Exception();
    }

}