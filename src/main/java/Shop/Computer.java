package Shop;

import java.util.HashMap;
import java.util.Map;

import myDBFramework.DataBaseObject;

public class Computer extends ShopObject{
	
	public Computer(){
		setType(ShopType.Computer);
	}
	
	//Computer(int id,String name, int price, int quant) {
	//	super(id,name, price, quant);
	//}

	@Override
	public Map<String, String> getMappedAttributes() {
		Map<String,String> result = new HashMap<>();
		result.put("Name", "name");
		result.put("Quantity", "quant");
		result.put("Price", "price");
		result.put("ComputerID", "id");
		//result.put("ComputerID", "id");
		return result;
	}

	@Override
	public String getTable() {
		// TODO Auto-generated method stub
		return "Computers";
	}

	@Override
	public String toString() {
		return getName();
	}
	
}
