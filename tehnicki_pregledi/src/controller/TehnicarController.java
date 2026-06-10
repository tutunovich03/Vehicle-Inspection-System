package controller;

import model.Tehnicar;
import model.Termin;
import model.TerminState;
import service.TerminService;

import java.io.*;
import java.util.*;

import dao.TerminDAO;

/**
 * 
 */
public class TehnicarController {
	
	TerminService tServ = new TerminService();

    /**
     * Default constructor
     */
    public TehnicarController() {
    }

    /**
     * @param t 
     * @return
     */
    public ArrayList<Termin> getTerminiDanas(Tehnicar t) { 
        ArrayList<Termin> list = null;
        try {
        	list = tServ.getTerminiDanas(t);
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return list;
    }

    /**
     * @param t
     */
    public void setNeodrzanTermin(Termin t) {
    	try {
        	tServ.setTerminState(t, TerminState.NEODRZAN);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }

    /**
     * @param t
     */
    public void setTermZapocet(Termin t) {
        try {
        	tServ.setTerminState(t, TerminState.U_TOKU);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }

    /**
     * @param t
     */
    public void setTerminPrekinut(Termin t) {
    	try {
        	tServ.setTerminState(t, TerminState.PREKINUT);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }

    /**
     * @param term 
     * @param rez
     */
    public void setRezultat(Termin term, boolean rez) {
    	try {
    		if(rez) {
    			tServ.setTerminState(term, TerminState.ISPRAVAN);
    		}
    		else 
    			tServ.setTerminState(term, TerminState.NEISPRAVAN);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }

}