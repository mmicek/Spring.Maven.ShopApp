package View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import Shop.Basket;
import shopApp.MainClass;

public class MainFrame extends JFrame {

	private ViewDefinable view = null;
	private static MainFrame frame = null;  //Singleton
	
	private MainFrame() { setSettings(); }
	
	private MainFrame(ViewDefinable view) {
		this.view = view;
		setSettings();
	}
	
	public static MainFrame getMainFrame() {
		if(frame == null) frame = new MainFrame();
		return frame;
	}
	
	public static MainFrame getMainFrame(ViewDefinable view) {
		if(frame == null)  frame = new MainFrame(view);
		if(frame.view == null) frame.setView(view);
		return frame;
	}
	
	public void setSettings() {
		//setUndecorated(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
		    @Override
		    public void run()
		    {
		        MainClass.toEnd = true;
		    }
		});
		
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(1000,600);
		setLocation(300,130);
		setIgnoreRepaint(true);
	}
	
	public void setScreen() {
		addComponents();
	}
	
	
	private void addComponents() {
		if(view == null) throw new IllegalArgumentException("No view panel in MainFrame");
		getContentPane().add(view.getPanel());
		this.revalidate();
		
//		JButton b = new JButton("button");
//		b.setBounds(100, 100, 200, 100);
//		JPanel panel = new JPanel();
//		panel.add(b);
//		panel.remove(b);
//		getContentPane().add(panel);
//		getContentPane().remove(panel);
	}
	
	public void changeView(ViewDefinable newView) {
		getContentPane().remove(this.view.getPanel());
		getContentPane().add(newView.getPanel());
		//repaint();

		this.revalidate();
		this.view = newView;
	}
	
	public void exit() {
		MainClass.toEnd = true;
		this.dispose();
	}
	
	@Autowired
	public void setView(ViewDefinable view) {
		this.view = view;
	}
	
	
}
