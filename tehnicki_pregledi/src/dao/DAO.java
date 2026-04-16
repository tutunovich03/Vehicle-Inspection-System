package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 */
public class DAO {

    public DAO() {
    }
    
    protected Connection con = null;
    protected Statement statement = null;
    protected PreparedStatement preparedStatement = null;
	protected ResultSet resultSet = null;
	protected ResultSetMetaData rsmd = null;
	
	public void connect() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost/tehnicki_pregledi","root","");
	}
	
	public void close() {
		try {
			if(resultSet!=null)
				resultSet = null;
			if(rsmd!=null)
				rsmd=null;
			if(preparedStatement!=null)
				preparedStatement = null;
			if(statement!=null)
				statement = null;
			if(con!=null)
				con = null;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
    
    public int countRows(String TableName) throws ClassNotFoundException, SQLException {
        connect();
        statement = con.createStatement();
        resultSet = statement.executeQuery("SELECT COUNT(*) FROM "+TableName+";");
        resultSet.next();
        int br = resultSet.getInt(1);
        close();
        
        return br;
    }

}