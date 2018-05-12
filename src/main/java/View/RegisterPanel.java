package View;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Model.DBUser;
import Model.User;

public class RegisterPanel extends AbstFrame implements ViewDefinable {
	private JPanel panel;
	
	public RegisterPanel() { createPanel(); }
	
	public JPanel getPanel() {
		return panel;
	}

	public void createPanel() {
		panel = new JPanel();
		panel.setLayout(null);
		JTextField login = new JTextField();
		JTextField password = new JTextField();
		JButton register = new JButton("Register");
		login.setBounds(width/2 - 100,160,200,40);
		password.setBounds(width/2 - 100,210,200,40);
		register.setBounds(width/2 - 110 + 64, 270, 100, 30);
		register.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DBUser u = new DBUser();
				u.setLogin(login.getText());
				u.setPassword(password.getText());
				u.setPerm(2);
				u.saveAutoID();
				MainFrame.getMainFrame().changeView(new LoginFrame());
			}
			
		});
		panel.add(login);
		panel.add(password);
		panel.add(register);
	}
	
}
