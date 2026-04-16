package dao;

import java.sql.SQLException;

public class SystemDAO extends DAO {
	
	
	public int readTrajanjePauze() throws ClassNotFoundException, SQLException {
		connect();
		statement = con.createStatement();
		resultSet = statement.executeQuery("SELECT value FROM `system_settings` WHERE `setting_key` = 'trajanje_pauze';");
		resultSet.next();
		int trajanje = Integer.parseInt(resultSet.getString(1));
		
		close();
		return trajanje;
	}

}
