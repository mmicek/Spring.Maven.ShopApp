package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Shop.Basket;
import Shop.ShopObject;

public class BasketPanel extends AbstFrame implements ViewDefinable {
	private JPanel panel;
	
	public BasketPanel() {
		createPanel();
	}
	
	@Override
	public JPanel getPanel() {
		return panel;
	}

	@Override
	public void createPanel() {
		panel = new JPanel();
		panel.setLayout(null);
		Basket basket = Basket.getBasket();
		
		int i = 0;
		for(ShopObject o : basket.getProducts()) {
			JLabel l = new JLabel(o.getName());
			l.setBounds(width/2 - 100,30*i + 140,200,25);
			l.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					Basket.remove(o);
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
			
			panel.add(l);
			i++;
		}
		
		JButton buy = new JButton("Buy");
		buy.setBounds(width/2-60,30*i+140,120,30);
		buy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<ShopObject> products = Basket.getBasket().getProducts();
				Basket.getBasket().removeAll();			
			}
			
		});
		
		JLabel label = new JLabel("Basket");
		label.setBounds(width/2 - 100,100,200,25);
		label.setFont(new Font("Serif", Font.PLAIN, 30));
		
		JButton button = new JButton("Back");
		button.setBounds(width - 140,height - 120,100,50);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.getMainFrame().changeView(new MainShopPanel(LoginFrame.getUser()));
			}
			
		});
		
		panel.add(buy);
		panel.add(button);
		panel.add(label);
	}

}
