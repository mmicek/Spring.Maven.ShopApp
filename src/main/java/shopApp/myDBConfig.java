package shopApp;

import myDBFramework.*;

public class myDBConfig implements DataBaseConfig {

	@Override
	public String getURL() {
		return "jdbc:sqlserver://localhost\\sqlexpress:65525";
	}

	@Override
	public String getUser() {
		return "exampleUsr";
	}

	@Override
	public String getPassword() {
		return "123";
	}

}
