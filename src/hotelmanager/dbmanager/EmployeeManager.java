package hotelmanager.dbmanager;

import hotelmanager.entities.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

public class EmployeeManager {
	private static EmployeeManager employeeManager;
	private CachedRowSet crs;

	public static EmployeeManager getEmployeeManager() {
		if (employeeManager == null)
			employeeManager = new EmployeeManager();
		return employeeManager;
	}

	private EmployeeManager() {
		try {
			new DataBaseManager();// the first time

			crs = new CachedRowSetImpl();
			crs.setUrl(DataBaseManager.info.getProperty("url"));
			crs.setUsername(DataBaseManager.info.getProperty("user"));
			crs.setPassword(DataBaseManager.info.getProperty("password"));
			crs.setCommand("SELECT * FROM employee");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Employee validateReceptionist(String ID, String password) {
		String getEmployeeByIDAndPassword_SQL = "SELECT * FROM employee WHERE ID = "
				+ ID + " AND password = '" + password + "' AND type < 3";
		return validate(getEmployeeByIDAndPassword_SQL);
	}

	public Employee validateManager(String ID, String password) {
		String getEmployeeByIDAndPassword_SQL = "SELECT * FROM employee WHERE ID = "
				+ ID + " AND password = '" + password + "' AND type < 2";
		return validate(getEmployeeByIDAndPassword_SQL);
	}

	public Employee validateAdministrator(String ID, String password) {
		String getEmployeeByIDAndPassword_SQL = "SELECT * FROM employee WHERE ID = "
				+ ID + " AND password = '" + password + "' AND type < 1";
		return validate(getEmployeeByIDAndPassword_SQL);
	}

	private Employee validate(String getEmployeeByIDAndPassword_SQL) {
		try {
			DataBaseManager db = new DataBaseManager();
			ResultSet rs = db.executeQuery(getEmployeeByIDAndPassword_SQL);
			if (rs != null && rs.next())
				return readData(rs);
		} catch (SQLException e) {
		}
		return null;
	}

	public List<Employee> getAllEmployees() {
		List<Employee> employeeList = new ArrayList<Employee>();

		String getEmployee_SQL = "SELECT * FROM employee";

		try {
			DataBaseManager db = new DataBaseManager();
			ResultSet rs = db.executeQuery(getEmployee_SQL);
			while (rs != null && rs.next())
				employeeList.add(readData(rs));
		} catch (SQLException e) {
		}
		return employeeList;
	}

	private Employee readData(ResultSet rs) throws SQLException {
		Employee employee = new Employee();

		employee.setID(rs.getLong("ID"));
		employee.setType(rs.getInt("type"));
		employee.setPassword(rs.getString("password"));
		employee.setName(rs.getString("name"));
		employee.setSex(rs.getInt("sex"));

		return employee;
	}

	public CachedRowSet getAll() {
		try {
			crs.execute();
		} catch (SQLException e) {
			for (Throwable t : e)
				t.printStackTrace();
			return null;
		}
		return crs;
	}

	public boolean update(Iterable<Employee> list) {
		DataBaseManager db = new DataBaseManager();
		boolean flag = false;
		for (Employee e : list) {
			String update = "UPDATE employee SET type = " + e.getType()
					+ ", password = '" + e.getPassword() + "', name = '"
					+ e.getName() + "', sex = " + e.getSex() + " WHERE ID = "
					+ e.getID();

			if (db.executeUpdate(update) == 1)
				flag = true;
		}
		return flag;
	}
}
