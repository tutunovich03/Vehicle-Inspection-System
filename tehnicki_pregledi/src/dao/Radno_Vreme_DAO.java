package dao;

import java.io.*;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import model.Radno_Vreme;

/**
 * 
 */
public class Radno_Vreme_DAO extends DAO{
    /**
     * Default constructor
     */
    public Radno_Vreme_DAO() {
    }

    /**
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * 
     */
    public void UpdateRV_Dan(DayOfWeek day, LocalTime vreme_od, LocalTime vreme_do) throws ClassNotFoundException, SQLException {
    	
    	String dan = switch(day) {
		case MONDAY -> "ponedeljak";
		case  TUESDAY -> "utorak";
		case  WEDNESDAY -> "sreda";
		case  THURSDAY -> "cetvrtak";
		case  FRIDAY -> "petak";
		case  SATURDAY -> "subota";
		case  SUNDAY -> "nedelja";
    	};
    	
        connect();
        preparedStatement = con.prepareStatement("UPDATE `radno_vreme_dani` SET `od`=?,`do`=? WHERE `dan` = ?");
        preparedStatement.setString(1, vreme_od.toString());
        preparedStatement.setString(2, vreme_do.toString());
        preparedStatement.setString(3, dan);
        
        preparedStatement.executeUpdate();
        close();
        
    }

    /**
     * 
     */
    public void createRV_Datuma() {
        // TODO implement here
    }

    /**
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * 
     */
    public void updateRV_Datuma(String datum, LocalTime vreme_od, LocalTime vreme_do, boolean stalno) throws ClassNotFoundException, SQLException {
    	connect();
    	preparedStatement = con.prepareStatement("INSERT INTO `radno_vreme_datumi`(`datum`, `od`, `do`, `stalno`) VALUES (?,?,?,?)");
    	preparedStatement.setString(1, datum);
    	preparedStatement.setString(2, vreme_od.toString());
    	preparedStatement.setString(3, vreme_do.toString());
    	preparedStatement.setBoolean(4, stalno);
    	
    	try {
    		preparedStatement.executeUpdate();
    	}
    	catch (SQLException e) {
    	    if (e.getErrorCode() == 1062) { 
    	        preparedStatement = con.prepareStatement("UPDATE `radno_vreme_datumi` SET `od`=?,`do`=? WHERE `datum` = ? AND `stalno` = ?;");
    	        preparedStatement.setString(1, vreme_od.toString());
    	        preparedStatement.setString(2, vreme_do.toString());
    	        preparedStatement.setString(3, datum);
    	        preparedStatement.setBoolean(4, stalno);
    	        
    	        preparedStatement.executeUpdate();
    	    }
    	}
    	
        close();
    }
    
    public Radno_Vreme readRadnoVreme(LocalDate datum) throws ClassNotFoundException, SQLException {
    	
    	String month = String.valueOf(datum.getMonthValue());
		String day = String.valueOf(datum.getDayOfMonth());
		String date = month + "-" +day;
		Radno_Vreme rv = new Radno_Vreme();
    	
    	connect();
    	preparedStatement = con.prepareStatement("SELECT `datum`,`od`,`do`,`stalno` FROM `radno_vreme_datumi` WHERE `datum`=?;");
    	preparedStatement.setString(1, date);
    	resultSet = preparedStatement.executeQuery();
    	if(resultSet.isBeforeFirst()) {
    		
    		System.out.println("nadjen datum");
    		while(resultSet.next()) {
    			LocalTime vreme_od = resultSet.getObject(2,LocalTime.class);
    			LocalTime vreme_do = resultSet.getObject(3, LocalTime.class);
    			
    			rv.setDatum(datum);
    			rv.setVreme_od(vreme_od);
    			rv.setVreme_do(vreme_do);
    			
    			if(resultSet.getBoolean(4) == false) {
    				close();
    				return rv;
    			}
    		}
    		
    		close();
    		return rv;
    		
    	}
    	
    	String dan = switch(datum.getDayOfWeek()) {
    		case MONDAY -> "ponedeljak";
    		case  TUESDAY -> "utorak";
    		case  WEDNESDAY -> "sreda";
    		case  THURSDAY -> "cetvrtak";
    		case  FRIDAY -> "petak";
    		case  SATURDAY -> "subota";
    		case  SUNDAY -> "nedelja";
    	};
    	
    	
    	preparedStatement = con.prepareStatement("SELECT * FROM `radno_vreme_dani` WHERE dan = ?;");
    	preparedStatement.setString(1, dan);
    	resultSet = preparedStatement.executeQuery();
    	resultSet.next();
    	
    	rv.setDatum(datum);
    	//System.out.println(rv.getDatum());
    	rv.setVreme_od(resultSet.getObject(2,LocalTime.class));
    	rv.setVreme_do(resultSet.getObject(3,LocalTime.class));
    	
    	close();
    	return rv;
    }

}