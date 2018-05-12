package Model;

public class User {
	private DBPrivileges privileges;
	private int userID;
	
	public User(DBPrivileges p,int id) {
		this.privileges = p;
		this.userID = id;
	}
	
	public DBPrivileges getPrivileges() {
		return privileges;
	}
	
	public int getID() {
		return userID;
	}
}
