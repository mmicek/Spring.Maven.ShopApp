package View;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.List;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import Model.User;
import Shop.ShopObject;
import Shop.ShopStore;
import Shop.ShopType;

public class MainShopPanel extends AbstFrame implements ViewDefinable{
	private User user;
	private JPanel panel;
	private ShopStore store;
	
	public MainShopPanel(User user) {
		this.user = user;
		this.store = new ShopStore();
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
		Map<ShopType,List<ShopObject>> products = store.getAllProducts();
		
		int i = 0;
		System.out.println("start");
		for(Map.Entry<ShopType,List<ShopObject>> ee : products.entrySet()) {
			JButton button = new JButton(ee.getKey().toString());
			button.setBounds(width/2 - 100,(height/products.size()-100)*i + 50,200,height/products.size() - 200);
			button.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e) {
					MainFrame.getMainFrame().changeView(new SpecTypePanel(ee.getValue(),ee.getKey()));
				}
				
			});
			
			
			panel.add(button);
			i++;
		}
		
		JButton exit = new JButton("Exit");
		exit.setBounds(width - 140,height - 120,100,50);
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.getMainFrame().exit();
			}
			
		});
		
		JButton basket = new JButton("Basket");
		basket.setBounds(width - 130,20,100,35);
		basket.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.getMainFrame().changeView(new BasketPanel());
			}
			
		});
		
		panel.add(basket);
		panel.add(exit);
	}
}
