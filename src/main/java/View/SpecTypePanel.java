package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Shop.ShopObject;
import Shop.ShopType;

public class SpecTypePanel extends AbstFrame implements ViewDefinable{
	private JPanel panel;
	private ShopType type;
	private List<ShopObject> products;
	
	public SpecTypePanel(SpecTypePanel panel) {
		this.type = panel.type;
		this.products = panel.products;
		createPanel();
	}
	
	public SpecTypePanel(List<ShopObject> products,ShopType type) {
		this.type = type;
		this.products = products;
		createPanel();
	}
	
	@Override
	public JPanel getPanel() {
		return panel;
	}

	@Override
	public void createPanel() {
		
		System.out.println(products.toString());
		
		panel = new JPanel();
		panel.setLayout(null);
		System.out.println(type.toString());
		JButton button = new JButton("Back");
		button.setBounds(width - 140,height - 120,100,50);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.getMainFrame().changeView(new MainShopPanel(LoginFrame.getUser()));
			}
			
		});
		
		int i = 0;
		for(ShopObject o : products) {
			if(o.getQuant() == 0)
				continue;
			JLabel t = new JLabel(o.getName());
			t.setBounds(this.width/2 - 150,i*25 + 70,300,25);
			t.setBackground(Color.white);
		    t.setOpaque(true);
		    SpecTypePanel thisO = this;
		    t.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					MainFrame.getMainFrame().changeView(new SpecObjectPanel(o,thisO));
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {}

				@Override
				public void mouseExited(MouseEvent arg0) {}

				@Override
				public void mousePressed(MouseEvent arg0) {}

				@Override
				public void mouseReleased(MouseEvent arg0) {}
		    	
		    });
			panel.add(t);
			i++;
		}
		
		panel.add(button);
	}
	

}
