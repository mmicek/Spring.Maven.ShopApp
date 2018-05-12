package shopApp;

import java.sql.Connection; 


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import DBHibernate.Computers;
import DBHibernate.HibernateUtil;
import Model.DataBase;
import View.LoginFrame;
import View.MainFrame;
//CTRL+TAB uzupelnianie
//ALT+ENTER importowanie
import myDBFramework.DataBaseFactory;

public class MainClass {
	public static volatile boolean toEnd = false;
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		//String dbURL = "jdbc:sqlserver://localhost\\sqlexpress:65525;user=exampleUsr;password=123";
		//DataBase.connectToDataBase(dbURL);
		
		DataBaseFactory.connect(myDBConfig.class);
	
		

		
		
		//AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigFile.class);
		//MainFrame frame = (MainFrame) context.getBean("frame");
		//frame.setScreen();
		
		MainFrame.getMainFrame(new LoginFrame()).setScreen();
		while(!toEnd) {}
		
		DataBaseFactory.close();
		System.out.println("Exit");
	}
	
	
}
