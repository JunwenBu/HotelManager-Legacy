package hotelmanager.entities;

public class Employee {

	public static final int Administrator = 0;

	public static final int Manager = 1;

	public static final int Receptionist = 2;

	public static final int MALE = 0;

	public static final int FEMALE = 1;

	public static String typeToString(int type) {
		switch (type) {
		case 0:
			return "����������";
		case 1:
			return "����";
		case 2:
			return "������������";
		default:
			return "����";
		}
	}

	public static String sexToString(int sex) {
		if (sex == 0)
			return "��";
		else
			return "��";
	}

	private long ID;

	private int type;

	private String password;

	private String name;

	private int sex;

	public long getID() {
		return ID;
	}

	public void setID(long id) {
		ID = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
}
