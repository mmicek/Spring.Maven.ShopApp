package Model;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
	private static Connection connection = null;
	private int port = 65525;
	
	private DataBase() {}
	
	public static void connectToDataBase(String dbURL) throws SQLException {
		connection = DriverManager.getConnection(dbURL);
		if (connection == null) {
		    throw new SQLException("Cant connect to SQL Database");
		}
		System.out.println("Connected");
	}
	
	public static Connection getConnection() {
		if(connection == null) throw new IllegalArgumentException("No database connection");
		return connection;
	}
	
	public static void end() throws SQLException {
		System.out.println("Connection closed");
		connection.close();
	}
}
