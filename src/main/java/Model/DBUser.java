package Model;

import java.util.HashMap;
import java.util.Map;

import myDBFramework.DataBaseObject;

public class DBUser extends DataBaseObject {
	
	private int id;
	private String login;
	private String pass;
	private int perm;
	
	@Override
	public Map<String, String> getMappedAttributes() {
		Map<String,String> result = new HashMap<>();
		result.put("UserID", "id");
		result.put("ULogin", "login");
		result.put("UPassword", "pass");
		result.put("Perm", "perm");
		return result;
	}

	@Override
	public String getTable() {
		return "Users";
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setPassword(String pass) {
		this.pass = pass;
	}
	
	public void setPerm(int perm) {
		this.perm = perm;
	}
	
	public int getID() {
		return this.id;
	}
	
	public int getPerm() {
		return this.perm;
	}

}
