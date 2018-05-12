package Shop;

import java.util.HashMap;
import java.util.Map;

import myDBFramework.DataBaseObject;

public abstract class ShopObject extends DataBaseObject {
	private int id;
	private String name;
	private int price;
	private int quant;
	private Map<String,String> additional;
	private ShopType type;
	
	public ShopObject() {}
	
	//ShopObject(int id,String name,int price,int quant){
	//	this.id = id;
	//	this.name = name;
	//	this.price = price;
	//	this.quant = quant;
	//	additional = new HashMap<>();
	//}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return id;
	}
	
	public void setType(ShopType type) {
		this.type = type;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getQuant() {
		return quant;
	}
	
	public ShopType getType() {
		return type;
	}
	
	public Map<String,String> getAdditional(){
		return additional;
	}
	
	public void addQuantity(int q) {
		this.quant += q;
	}
	
}
