package hotelmanager.entities;

public class Customer {

	private long ID;

	private String name;

	private int sex;

	private int discount;

	private float balance;

	public long getID() {
		return ID;
	}

	public void setID(long id) {
		ID = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public float getBalance() {
		return balance;
	}
}
