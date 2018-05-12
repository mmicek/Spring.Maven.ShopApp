package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import myDBFramework.DataBaseFactory;

public class ConnectionLogIn {
	
	public ConnectionLogIn(){}
	
	public Result logIn(String login,String password) {
		try {
			String sql = "select * from Users where ULogin = '" + login + "' and UPassword = '" + password + "'";
			ResultSet result = DataBaseFactory.executeQuery(sql);
			
			if(!result.next()) return new Result(DBPrivileges.NoPr,0); 
			int i = result.getInt("Perm");
			int id = result.getInt("UserID");
			return new Result(DBPrivileges.convert(i),id);
			
		} catch (SQLException e) {}
		
		throw new IllegalArgumentException("ConectioLogIn: Cannot Connect to DB");
	}
	
	public class Result{
		public DBPrivileges p;
		public int id;
		
		Result(DBPrivileges p,int id){
			this.p = p;
			this.id = id;
		}
			
	}
}
