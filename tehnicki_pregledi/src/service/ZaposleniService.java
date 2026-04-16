package service;

import model.RadnoMestoZaposlenog;
import model.Sekretarica;
import model.Tehnicar;
import model.Zaposleni;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import dao.ZaposleniDAO;
import exceptions.UserCreateException;
import exceptions.ZaposleniCreateException;

/**
 * 
 */
public class ZaposleniService {
	
	ZaposleniDAO zDAO = new ZaposleniDAO();

    /**
     * Default constructor
     */
    public ZaposleniService() {
    }

    /**
     * @param z
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void addZaposleni(Zaposleni z) throws ClassNotFoundException, SQLException {
        verifyZaposleni(z);
        RadnoMestoZaposlenog rms = null;
        
        if(z instanceof Tehnicar) {
			rms = RadnoMestoZaposlenog.TEHNICAR;
		}
        if(z instanceof Sekretarica) {
        	rms = RadnoMestoZaposlenog.SEKRETARICA;
        }
        
        zDAO.createZaposleni(z, rms);
    }

    /**
     * @param z
     */
    public void verifyZaposleni(Zaposleni z) {
        // grupa_id je fk u tabeli zaposleni tkd moze imati samo vrednosti koje imaju pk redova u tabeli grupa,ako se pokusa unos neke druge vrednosti dogodice se sql exception, treba ga handlovati
    	
    }

    /**
     * @param z
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void removeZaposleni(Zaposleni z) throws ClassNotFoundException, SQLException {
        zDAO.deleteZaposleni(z);
    }

    /**
     * @return
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws UserCreateException 
     */
    public ArrayList<Zaposleni> zaposleniList(RadnoMestoZaposlenog rmz) throws ClassNotFoundException, SQLException, UserCreateException {
        return zDAO.readZapsoleniList(rmz);
    }

}