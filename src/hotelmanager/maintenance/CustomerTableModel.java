package hotelmanager.maintenance;

import hotelmanager.dbmanager.CustomerManager;
import hotelmanager.entities.Customer;

import java.util.HashSet;
import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class CustomerTableModel extends AbstractTableModel {
	List<Customer> all;
	HashSet<Customer> changed;
	private boolean isEditable;

	public CustomerTableModel() {
		all = CustomerManager.getCustomerManager().getAllCustomers();
		changed = new HashSet<Customer>();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Long.class;
		case 1:
			return String.class;
		case 2:
			return Integer.class;
		case 3:
			return Integer.class;
		case 4:
			return Float.class;

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
		Customer temp = all.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return temp.getID();
		case 1:
			return temp.getName();
		case 2:
			return temp.getSex();
		case 3:
			return temp.getDiscount();
		case 4:
			return temp.getBalance();

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
		Customer temp = all.get(rowIndex);
		String s = value.toString();
		switch (columnIndex) {
		case 0:
			temp.setID(Long.valueOf(s));
			break;
		case 1:
			temp.setName(s);
			break;
		case 2:
			temp.setSex((Integer) value);
			break;
		case 3:
			int d = Integer.valueOf(s);
			if (d < 1)
				d = 1;
			else if (d > 100)
				d = 100;
			temp.setDiscount(d);
			break;
		case 4:
			float f = Float.valueOf(s);
			if (f < 0)
				f = 0;
			else if (f > 9999.0)
				f = 9999.0f;
			temp.setBalance(f);
			break;
		}

		changed.add(temp);
	}

	public void setEditable(boolean e) {
		isEditable = e;
	}

	public void save() {
		CustomerManager.getCustomerManager().update(changed);
	}
}
