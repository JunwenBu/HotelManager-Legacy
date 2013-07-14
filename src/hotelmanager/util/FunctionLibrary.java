package hotelmanager.util;

import java.util.Date;

public class FunctionLibrary {
	/**
	 * ����������������
	 */
	public static String getTypeName(int roomType) {
		switch (roomType) {
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

	/**
	 * ��������������������������
	 */
	public static Date getLaterDate(Date date) {
		return new Date(
				((date.getTime() - 72000000) / 86400000 + 2) * 86400000 + 14400000);
	}

	/**
	 * ������������
	 */
	public static String getEmployeeTypeName(int employee) {
		switch (employee) {
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
}
