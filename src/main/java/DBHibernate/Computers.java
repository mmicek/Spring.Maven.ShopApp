package DBHibernate;

public class Computers {
	private int computerID;
	private String name;
	private int producentID;
	private int quantity;
	private int price;
	
	public Computers() {}
	
	public int getComputerID() {
		return computerID;
	}
	
	public void setComputerID(int id) {
		this.computerID = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setProducentID(int id) {
		this.producentID = id;
	}
	
	public void setQuantity(int quant) {
		this.quantity = quant;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
}
