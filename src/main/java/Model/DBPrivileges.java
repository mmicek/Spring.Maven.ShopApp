package Model;

public enum DBPrivileges {
	Admin,Owner,User,NoPr;
	
	public static DBPrivileges convert(int x) {
		return values()[x];
	}
}
