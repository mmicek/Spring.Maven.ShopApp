package Shop;

import java.util.HashMap;
import java.util.Map;

public class Keyboard extends ShopObject{

	@Override
	public Map<String, String> getMappedAttributes() {
		Map<String,String> result = new HashMap<>();
		result.put("Name", "name");
		result.put("Quantity", "quant");
		result.put("Price", "price");
		result.put("KeyboardID", "id");
		return result;
	}

	@Override
	public String getTable() {
		return "Keyboard";
	}

	@Override
	public String toString() {
		return getName();
	}
	
}
