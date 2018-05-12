package myDBFramework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DataBaseFactory {
	private static Connection connection = null;
	
	private DataBaseFactory() {}
	
	/**
	 * Connecting to MSSQL local database
	 * @param config file which is implementing DataBaseConfig.
	 * Param is like [Class name].class
	 * @return this object, like singleton
	 */
	public static DataBaseFactory connect(Class c) {	
		try {
			DataBaseConfig config;
			config = (DataBaseConfig) Class.forName(c.getName()).newInstance();
			
			String dbURL = config.getURL() + ";user=" + config.getUser() 
				+ ";password=" + config.getPassword();
			connection = DriverManager.getConnection(dbURL);
			if (connection == null) {
		    	throw new SQLException("Cant connect to SQL Database");
			}
			
		}catch(SQLException | IllegalAccessException 
				| ClassNotFoundException | InstantiationException e) {
			e.printStackTrace();
		}
		
		return new DataBaseFactory();
	}
	
	
	/**
	 * @param sqlQuery
	 * @return result of SQL query as ResultSet from JDBC Framework
	 */
	public static ResultSet executeQuery(String sqlQuery) {
		try {
			
			if(!sqlQuery.toLowerCase().contains("SELECT".toLowerCase())) {
				Statement stm = connection.createStatement();
				stm.execute(sqlQuery);
				return null;
			}
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sqlQuery);
			return rs;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	static Connection getConnection() {
		if(connection == null)
			throw new IllegalArgumentException("Error: Missing connection to DataBase");
		return connection;
	}
	
	/**
	 * Closing connection with database
	 */
	public static void close() {
		try {
			connection.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
