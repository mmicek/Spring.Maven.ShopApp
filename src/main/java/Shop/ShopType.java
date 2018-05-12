package Shop;

public enum ShopType {
	Computer,Keyboard; //Name the same as Object that we want to mapped from DB

	public static ShopType getEnum(String s) {
		for(ShopType type : ShopType.values()) {
			if(type.toString().equals(s))
				return type;
		}
		return null;
	}
}
