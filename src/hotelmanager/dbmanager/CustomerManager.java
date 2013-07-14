package hotelmanager.dbmanager;

import hotelmanager.entities.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

public class CustomerManager {
	private static CustomerManager customerManager;
	private CachedRowSet crs;

	/**
	 * @return a instance of CustomerManager
	 */
	public static CustomerManager getCustomerManager() {
		if (customerManager == null) {
			customerManager = new CustomerManager();
		}
		return customerManager;
	}

	private CustomerManager() {
		try {
			crs = new CachedRowSetImpl();
			crs.setCommand("SELECT * FROM customer");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean addNewCustomer(Customer newCustomer) {
		String addCustomer_SQL = "INSERT INTO customer VALUES ("
				+ newCustomer.getID() + ", '" + newCustomer.getName() + "', "
				+ newCustomer.getSex() + ", " + newCustomer.getDiscount()
				+ ", " + newCustomer.getBalance() + ")";

		if (new DataBaseManager().executeUpdate(addCustomer_SQL) == 1)
			return true;
		return false;
	}

	public Customer getCustomerByID(long ID) {
		String getCustomerByID_SQL = "SELECT * FROM customer WHERE ID = " + ID;

		try {
			DataBaseManager db = new DataBaseManager();
			ResultSet rs = db.executeQuery(getCustomerByID_SQL);
			if (rs != null && rs.next())
				return readData(rs);
		} catch (SQLException e) {
		}
		return null;
	}

	public List<Customer> getCustomersByName(String name) {
		List<Customer> customerList = new ArrayList<Customer>();

		String getCustomerByName_SQL = "SELECT * FROM customer WHERE name = '"
				+ name + "'";

		try {
			DataBaseManager db = new DataBaseManager();
			ResultSet rs = db.executeQuery(getCustomerByName_SQL);
			while (rs != null && rs.next())
				customerList.add(readData(rs));
		} catch (SQLException e) {
		}
		return customerList;
	}

	public List<Customer> getAllCustomers() {
		List<Customer> customerList = new ArrayList<Customer>();

		String getCustomer_SQL = "SELECT * FROM customer";

		try {
			DataBaseManager db = new DataBaseManager();
			ResultSet rs = db.executeQuery(getCustomer_SQL);
			while (rs != null && rs.next())
				customerList.add(readData(rs));
		} catch (SQLException e) {
		}
		return customerList;
	}

	private Customer readData(ResultSet rs) throws SQLException {
		Customer customer = new Customer();

		customer.setID(rs.getLong("ID"));
		customer.setName(rs.getString("name"));
		customer.setSex(rs.getInt("sex"));
		customer.setDiscount(rs.getInt("discount"));
		customer.setBalance(rs.getFloat("balance"));

		return customer;
	}

	public CachedRowSet getAll() {
		try {
			crs.execute(DataBaseManager.conn);
		} catch (SQLException e) {
			for (Throwable t : e)
				t.printStackTrace();
			return null;
		}
		return crs;
	}

	public boolean update(Iterable<Customer> changed) {
		DataBaseManager db = new DataBaseManager();
		boolean flag = false;
		for (Customer c : changed) {
			String update = "UPDATE customer SET name = '" + c.getName()
					+ "', sex = " + c.getSex() + ", discount = "
					+ c.getDiscount() + ", balance = " + c.getBalance()
					+ " WHERE ID = " + c.getID();

			if (db.executeUpdate(update) == 1)
				flag = true;
		}
		return flag;
	}

	public boolean updateBlance(long ID, float change) {
		String updateBalance_SQL = "UPDATE customer SET balance = balance "
				+ ((change < 0) ? "" : "+") + change + " WHERE ID = " + ID;

		if (new DataBaseManager().executeUpdate(updateBalance_SQL) == 1)
			return true;

		return false;
	}
}
