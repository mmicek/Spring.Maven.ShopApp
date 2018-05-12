package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.stereotype.Component;

import Model.ConnectionLogIn;
import Model.DBPrivileges;
import Model.User;
import Shop.Basket;

@Component
public class LoginFrame extends AbstFrame implements ViewDefinable {
	private JPanel panel;
	private static User user;
	
	public LoginFrame() { createPanel(); }

	public void createPanel() {
		panel = new JPanel();
		panel.setLayout(null);
		JButton loginB = new JButton("Login");
		loginB.setBounds(width/2 - 110, 270, 100, 30);
		JButton registerB = new JButton("Register");
		registerB.setBounds(width/2 + 10,270,100,30);
		final JTextField logB = new JTextField("");
		logB.setBounds(width/2 - 100,160,200,40);
		final JTextField passB = new JTextField("");
		passB.setBounds(width/2 - 100,210,200,40);
		
		loginB.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String login = logB.getText();
				String pass = passB.getText();
				ConnectionLogIn.Result r = new ConnectionLogIn().logIn(login,pass);
				DBPrivileges permission = r.p;
				if(permission == DBPrivileges.NoPr) {
					System.out.println("Bledne haslo lub login");
					return;
				}
				User user = new User(permission,r.id);
				setUser(user);
				MainShopPanel shopPanel = new MainShopPanel(user);
				Basket.setBasket();
				MainFrame.getMainFrame().changeView(shopPanel);
			}
			
		});
		registerB.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				MainFrame.getMainFrame().changeView(new RegisterPanel());
			}
			
		});
		
		panel.add(logB);
		panel.add(passB);
		panel.add(loginB);
		panel.add(registerB);
	}
	
	public static User getUser() {
		return user;
	}
	
	private static void setUser(User u) {
		user = u;
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
