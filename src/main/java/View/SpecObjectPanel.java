package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Shop.Basket;
import Shop.ShopObject;

public class SpecObjectPanel extends AbstFrame implements ViewDefinable{
	private JPanel panel;
	private ShopObject product;
	private SpecTypePanel prevPanel;
	
	public SpecObjectPanel(ShopObject object,SpecTypePanel prevPanel) {
		this.product = object;
		this.prevPanel = prevPanel;
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
		JButton back = new JButton("Back");
		back.setBounds(width - 140,height - 120,100,50);
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.getMainFrame().changeView(new SpecTypePanel(prevPanel));
			}
			
		});
		
		JLabel name = new JLabel(product.getName());
		name.setBounds(width/2 - 200,100,400,25);
		name.setFont(new Font("Serif", Font.PLAIN, 30));
		JLabel quant = new JLabel("Quantity: "+Integer.toString(product.getQuant()));
		quant.setBounds(width/2 - 100,130,200,25);
		JLabel price = new JLabel("Price: "+Integer.toString(product.getPrice()));
		price.setBounds(width/2 - 100,160,200,25);
		
		if(product.getQuant() != 0) {
			JButton buy = new JButton("Add to basket");
			buy.setBounds(width/2 - 160,200,200,25);
			buy.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					Basket.addToBasket(product);
					modifyProductQuant(product);
				}
			
			});
			panel.add(buy);
		}
		
		panel.add(quant);
		panel.add(price);
		panel.add(name);
		panel.add(back);
	}
	
	private void modifyProductQuant(ShopObject product) {
		product.addQuantity(-1);
		product.update();
		MainFrame.getMainFrame().changeView(new SpecObjectPanel(product,prevPanel));
	}

}
