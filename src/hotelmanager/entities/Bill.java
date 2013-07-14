package hotelmanager.entities;

public class Bill {

	private long ID;

	private long customerID;

	private int roomID;

	private int roomType;

	private long employeeID;

	private float amount;

	public Bill(Room r) {
		ID = System.currentTimeMillis();
		customerID = r.getCustomerID();
		roomID = r.getID();
		roomType = r.getType();
	}

	public Bill() {
	}

	public long getID() {
		return ID;
	}

	public void setID(long id) {
		ID = id;
	}

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public int getRoomType() {
		return roomType;
	}

	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}

	public long getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(long employeeID) {
		this.employeeID = employeeID;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
}
