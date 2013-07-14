package hotelmanager.login;

import hotelmanager.StartUp;
import hotelmanager.dbmanager.EmployeeManager;
import hotelmanager.entities.Employee;

public class LoginController {
	Employee employee;
	EmployeeManager em;
	LoginDialog dialog;
	int minType;

	public LoginController(StartUp startUp) {
		startUp.update("Connecting to database...", 10);
		em = EmployeeManager.getEmployeeManager();
		startUp.update("Login to HotelManger...", 20);
		dialog = new LoginDialog(this);
	}

	public Employee getEmployee(int type) {
		employee = null;
		minType = type;
		dialog.type.setSelectedIndex(minType);
		dialog.username.setText("");
		dialog.password.setText("");
		dialog.setVisible(true);
		return employee;
	}

	@SuppressWarnings("deprecation")
	boolean authentication() {
		String ID = dialog.username.getText().trim();
		String password = dialog.password.getText().trim();
		switch (dialog.type.getSelectedIndex()) {
		case 0:
			employee = em.validateAdministrator(ID, password);
			break;

		case 1:
			employee = em.validateManager(ID, password);
			break;

		case 2:
			employee = em.validateReceptionist(ID, password);
			break;
		}

		if (employee == null)
			return false;
		else
			return true;
	}
}
