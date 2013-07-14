package hotelmanager.dbmanager;

import hotelmanager.entities.Bill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillManager {
	private static BillManager billManager;

	public static BillManager getBillManager() {
		if (billManager == null) {
			billManager = new BillManager();
		}
		return billManager;
	}

	private BillManager() {
	}

	public boolean addNewBill(List<Bill> billList) {
		boolean flag = false;
		DataBaseManager dbm = new DataBaseManager();
		int count = 1;
		for (Bill newBill : billList) {
			String addBill_SQL = "INSERT INTO bill VALUES ("
					+ (newBill.getID() + count) + ", "
					+ newBill.getCustomerID() + ", " + newBill.getRoomID()
					+ ", " + newBill.getRoomType() + ", "
					+ newBill.getEmployeeID() + ", " + newBill.getAmount()
					+ ")";
			count++;

			if (dbm.executeUpdate(addBill_SQL) == 1)
				flag = true;
		}

		return flag;
	}

	public boolean deleteBillByID(long beginID, long endID) {
		String deleteBillByID_SQL = "DELETE FROM bill WHERE ID BETWEEN "
				+ beginID + " AND " + endID;

		if (new DataBaseManager().executeUpdate(deleteBillByID_SQL) == 1)
			return true;
		return false;
	}

	public List<List<Bill>> getBills(long beginID, long endID, long customerID,
			int choice) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM bill");
		if (beginID != 0) {
			sb.append(" WHERE ID BETWEEN " + beginID + " AND " + endID);
			if (customerID != 0)
				sb.append(" AND customerID = " + customerID);
		} else {
			if (customerID != 0)
				sb.append(" WHERE customerID = " + customerID);
		}

		switch (choice) {
		case 0:
			sb.append(" ORDER BY roomType, ID");
			break;
		case 1:
			sb.append(" ORDER BY roomID, ID");
			break;
		case 2:
			sb.append(" ORDER BY employeeID, ID");
			break;

		default:
			break;
		}

		return getBillListEx(sb.toString(), choice);
	}

	private List<List<Bill>> getBillListEx(String sql, int choice) {
		List<List<Bill>> billsLists = new ArrayList<List<Bill>>();
		try {
			DataBaseManager db = new DataBaseManager();
			ResultSet rs = db.executeQuery(sql);

			List<Bill> temp = new ArrayList<Bill>();
			Bill bill;
			long key = -1;
			if (rs.next()) {
				bill = readData(rs);
				temp.add(bill);
				key = getKey(bill, choice);

				while (rs.next()) {
					bill = readData(rs);
					if (key != getKey(bill, choice)) {
						key = getKey(bill, choice);

						billsLists.add(temp);
						temp = new ArrayList<Bill>();
					}
					temp.add(bill);
				}

				if (temp.size() > 0)
					billsLists.add(temp);
			}
		} catch (SQLException e) {
		}
		return billsLists;
	}

	private long getKey(Bill b, int choice) {
		switch (choice) {
		case 0:
			return b.getRoomType();
		case 1:
			return b.getRoomID();
		case 2:
			return b.getEmployeeID();
		}
		return 0;
	}

	private Bill readData(ResultSet rs) throws SQLException {
		Bill bill = new Bill();

		bill.setID(rs.getLong("ID"));
		bill.setCustomerID(rs.getLong("customerID"));
		bill.setRoomID(rs.getInt("roomID"));
		bill.setRoomType(rs.getInt("roomType"));
		bill.setEmployeeID(rs.getLong("employeeID"));
		bill.setAmount(rs.getFloat("amount"));

		return bill;
	}

	// public static void main(String[] args)
	// {
	// new DataBaseManager();
	// BillManager bm = BillManager.getBillManager();
	//
	// List<Customer> c = CustomerManager.getCustomerManager()
	// .getAllCustomers();
	// int cx;
	// List<Room> r = RoomManager.getRoomManager().getAllRooms();
	// int rx;
	//
	// List<Employee> e = EmployeeManager.getEmployeeManager()
	// .getAllEmployees();
	// int ex;
	//
	// List<Bill> b = new ArrayList<Bill>();
	//
	// Bill temp;
	//
	// long id = new Date().getTime();
	//
	// for (int i = 0; i < 12; i++, id -= 16 * 60 * 60 * 1000)
	// {
	// for (int j = 0; j < 10; j++)
	// {
	// temp = new Bill();
	// cx = (int) (c.size() * Math.random());
	// rx = (int) (r.size() * Math.random());
	// ex = (int) (e.size() * Math.random());
	//
	// temp.setID(id);
	// temp.setCustomerID(c.get(cx).getID());
	// temp.setRoomID(r.get(rx).getID());
	// temp.setRoomType((r.get(rx).getType()));
	// temp.setEmployeeID(e.get(ex).getID());
	// temp.setAmount(300);
	//
	// b.add(temp);
	// }
	// }
	//
	// bm.addNewBill(b);
	// }
}
