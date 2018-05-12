package Shop;

import java.util.LinkedList;
import java.util.List;

import Model.DataBasket;
import Model.User;
import View.BasketPanel;
import View.LoginFrame;
import View.MainFrame;
import myDBFramework.DataBaseObject;

public class Basket {
	private List<ShopObject> products;
	private List<ShopObject> addedProducts;
 	private static Basket basket = null;
	
	private Basket() {
		initBasket();
	}
	
	public List<ShopObject> getProducts(){
		return products;
	}
	
	public static void setBasket() {
		getBasket();	
	}
	
	public static Basket getBasket() {
		if(basket == null) 
			basket = new Basket();
		return basket; 
	}
	
	private static boolean change = true;
	public static void remove(ShopObject product) {
		if(change) {
			getBasket().products.remove(product);
			if(getBasket().addedProducts.contains(product)) {
				getBasket().addedProducts.remove(product);
			}
		}
		DataBasket data = new DataBasket();
		data.set(LoginFrame.getUser().getID(), product.getID(), product.getType().toString());
		String sqlQuery = "SET ROWCOUNT 1; DELETE FROM BASKET WHERE USERID = '" 
				+ data.getUserID() + "' AND " + "PRODUCTID = '" + data.getID() 
				+ "' AND " + "TYPE = '" + data.getType() + "'; SET ROWCOUNT 0;" ;
		
		data.executeQuery(sqlQuery);
		if(change) {
			product.addQuantity(1);
			product.update();
		}
		if(change)
			MainFrame.getMainFrame().changeView(new BasketPanel());
	}
	
	public static void removeAll() {
		for(ShopObject o : getBasket().products) {
			change = false;
			remove(o);
			change = true;
		}
		getBasket().addedProducts = new LinkedList<>();
		getBasket().products = new LinkedList<>();
		MainFrame.getMainFrame().changeView(new BasketPanel());
	}
	
	private void initBasket() {
		
		products = new LinkedList<>();
		addedProducts = new LinkedList<>();
		User u = LoginFrame.getUser();
		List<DataBaseObject> result = new DataBasket().executeQuery("SELECT * FROM BASKET WHERE UserID = '" 
				+ u.getID() + "'").getMappedObjects();
		
		for(DataBaseObject o : result) {
			DataBasket product = (DataBasket) o;
			
			List<ShopObject> list = ShopStore.getProducts(ShopType.getEnum(product.getType()));
			ShopObject resO = null;
			for(ShopObject so : list) {
				if(so.getID() == product.getID()) {
					resO = so; 
				}
			}
			if(resO != null) {
				products.add(resO);
			}
		}
	}
	
	public static void addToBasket(ShopObject product) {
		getBasket().products.add(product);
		getBasket().addedProducts.add(product);
		saveBasket();
	}
	
	public static void saveBasket() {
		for(ShopObject o : getBasket().addedProducts) {
			DataBasket data = new DataBasket();
			data.set(LoginFrame.getUser().getID(), o.getID(), o.getType().toString());
			data.saveWithID();
			getBasket().addedProducts.remove(o);
		}
	}
	
	@Override
	public String toString() {
		List<String> result = new LinkedList<>();
		for(ShopObject o : products) {
			result.add(o.getName());
		}
		return result.toString();
	}
}
