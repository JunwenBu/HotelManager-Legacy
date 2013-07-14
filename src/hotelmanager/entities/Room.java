package hotelmanager.entities;

import java.sql.Timestamp;

public class Room {

	public static final int Economic = 0;

	public static final int SingleStandard = 1;

	public static final int DoubleStandard = 2;

	public static final int Superior = 3;

	public static final int Deluxe = 4;

	public static final int Business = 5;

	public static final int Executive = 6;

	public static final int CleanAndEmpty = 0;

	public static final int Reserved = 1;

	public static final int Occupied = 2;

	public static final int Dirty = 3;

	public static String typeToString(int type) {
		switch (type) {
		case 0:
			return "������";
		case 1:
			return "����������";
		case 2:
			return "����������";
		case 3:
			return "������";
		case 4:
			return "������";
		default:
			return "����";
		}
	}

	public static String stateToString(int state) {
		switch (state) {
		case 0:
			return "������";
		case 1:
			return "����������";
		case 2:
			return "����������";
		case 3:
			return "����";
		default:
			return "����";
		}
	}

	/**
	 * ����������
	 */
	private int ID;
	/**
	 * ����������,�� {@link #Economic}, {@link #SingleStandard},
	 * {@link #DoubleStandard}, {@link #Superior}, {@link #Deluxe},
	 * {@link #Business}, {@link #Executive}, ��������
	 */
	private int type;
	/**
	 * ����������
	 */
	private float price;
	/**
	 * ������������������
	 */
	private String description;
	/**
	 * ��������������.�� {@link #CleanAndEmpty}, {@link #Reserved},
	 * {@link #Occupied}, {@link #Dirty}��������
	 */
	private int state;
	/**
	 * ����������������������������.���� state = 1, ��������������������
	 */
	private Timestamp checkInDate;
	/**
	 * ��������������������������
	 */
	private Timestamp checkOutDate;
	/**
	 * ������������������ID��, ��ID��������������������������������
	 */
	private long customerID;

	public Room() {
		state = 0;
	}

	public int getID() {
		return ID;
	}

	public void setID(int id) {
		ID = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public Timestamp getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Timestamp checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Timestamp getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Timestamp checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}
}
