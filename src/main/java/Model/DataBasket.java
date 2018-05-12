package Model;

import java.util.HashMap;
import java.util.Map;

import Shop.ShopType;
import myDBFramework.DataBaseObject;

public class DataBasket extends DataBaseObject{
	private int productID;
	private String type;
	private int userID;
	
	public int getID() {
		return productID;
	}
	
	public String getType() {
		return type;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void set(int userid,int productid,String type) {
		this.productID = productid;
		this.type = type;
		this.userID = userid;
	}
	
	@Override
	public Map<String, String> getMappedAttributes() {
		Map<String,String> result = new HashMap<>();
		result.put("ProductID","productID");
		result.put("Type", "type");
		result.put("UserID", "userID");
		return result;
	}

	@Override
	public String getTable() {
		return "Basket";
	}

}
