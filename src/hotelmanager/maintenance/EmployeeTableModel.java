package hotelmanager.maintenance;

import hotelmanager.dbmanager.EmployeeManager;
import hotelmanager.entities.Employee;

import java.util.HashSet;
import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class EmployeeTableModel extends AbstractTableModel {
	List<Employee> all;
	HashSet<Employee> changed;
	private boolean isEditable;

	public EmployeeTableModel() {
		all = EmployeeManager.getEmployeeManager().getAllEmployees();
		changed = new HashSet<Employee>();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Long.class;
		case 1:
			return Integer.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		case 4:
			return Integer.class;

		default:
			return Object.class;
		}
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "ID";
		case 1:
			return "����";
		case 2:
			return "����";
		case 3:
			return "����";
		case 4:
			return "����";
		default:
			return "";
		}
	}

	@Override
	public int getRowCount() {
		return all.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Employee temp = all.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return temp.getID();
		case 1:
			return temp.getType();
		case 2:
			return temp.getPassword();
		case 3:
			return temp.getName();
		case 4:
			return temp.getSex();

		default:
			return "";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (isEditable && columnIndex > 0)
			return true;
		else
			return false;
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Employee temp = all.get(rowIndex);
		String s = value.toString();
		switch (columnIndex) {
		case 0:
			temp.setID(Long.valueOf(s));
			break;
		case 1:
			temp.setType((Integer) value);
			break;
		case 2:
			temp.setPassword(s);
			break;
		case 3:
			temp.setName(s);
			break;
		case 4:
			temp.setSex((Integer) value);
			break;
		}

		changed.add(temp);
	}

	public void save() {
		System.out
				.println(EmployeeManager.getEmployeeManager().update(changed));
		;
	}

	public void setEditable(boolean selected) {
		isEditable = selected;
	}
}
