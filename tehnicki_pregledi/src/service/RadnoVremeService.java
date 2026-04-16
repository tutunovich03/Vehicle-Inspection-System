package service;

import java.io.*;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import dao.Radno_Vreme_DAO;
import model.Radno_Vreme;

/**
 * 
 */
public class RadnoVremeService {

    /**
     * Default constructor
     */
	Radno_Vreme_DAO rvDAO = new Radno_Vreme_DAO();
	
	
    public RadnoVremeService() {
    }

    /**
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * 
     */
    public void setRV_Dana(DayOfWeek day, LocalTime vreme_od, LocalTime vreme_do) throws ClassNotFoundException, SQLException {
        rvDAO.UpdateRV_Dan(day, vreme_od, vreme_do);
    }

    /**
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * 
     */
    public void setRV_Datuma(String datum, LocalTime vreme_od, LocalTime vreme_do, boolean stalno) throws ClassNotFoundException, SQLException { //datum mora biti string posto idu samo mesec i dan, ne godina
        rvDAO.updateRV_Datuma(datum, vreme_od, vreme_do, stalno);
    }

}