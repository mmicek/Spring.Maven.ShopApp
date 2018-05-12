package Shop;

import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import myDBFramework.DataBaseObject;
import myDBFramework.DataBaseResult;

public class ShopStore {
	private static Map<ShopType,List<ShopObject>> products;
	
	public ShopStore() {
		products = new HashMap<>();
		addProducts();
	}
	
	public Map<ShopType,List<ShopObject>> getAllProducts(){
		return products;
	}
	
	/**
	 * Adding all products from DB to shop application
	 * New group of products as new table need to add manually there
	 * 
	 */
	public void addProducts() {
		try {
		
			for(ShopType type : ShopType.values()) {
				DataBaseObject c = (DataBaseObject) Class.forName("Shop."+type.toString()).newInstance();
				DataBaseResult result = (DataBaseResult) c.getClass().getSuperclass().getSuperclass()
						.getDeclaredMethod("selectAll", null).invoke(c, null);
				List<DataBaseObject> list = result.getMappedObjects();
				List<ShopObject> objList = new LinkedList<>();
				for(DataBaseObject o : list) {
					ShopObject x = (ShopObject) o;
					x.setType(type);
					objList.add(x);
				}
				
				products.put(type, objList);
			}
		
		}catch(Exception e) {e.printStackTrace();}
	}
	
	public static List<ShopObject> getProducts(ShopType type){
		return products.get(type);
	}
	
}
