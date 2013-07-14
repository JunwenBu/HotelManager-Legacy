package hotelmanager.maintenance;

import hotelmanager.dbmanager.RoomManager;
import hotelmanager.entities.Room;

import java.util.HashSet;
import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class RoomTableModel extends AbstractTableModel {
	List<Room> all;
	HashSet<Room> changed;
	private boolean isEditable;

	public RoomTableModel() {
		all = RoomManager.getRoomManager().getAllRooms();
		changed = new HashSet<Room>();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 1:
			return Integer.class;
		case 2:
			return Float.class;
		case 3:
			return String.class;

		default:
			return Object.class;
		}
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "ID";
		case 1:
			return "��������";
		case 2:
			return "��������";
		case 3:
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
		Room temp = all.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return temp.getID();
		case 1:
			return temp.getType();
		case 2:
			return temp.getPrice();
		case 3:
			return temp.getDescription();

		default:
			return "";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (isEditable && columnIndex > 0 && columnIndex < 4)
			return true;
		else
			return false;
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Room temp = all.get(rowIndex);
		String s = value.toString();
		switch (columnIndex) {
		case 0:
			temp.setID(Integer.valueOf(s));
			break;
		case 1:
			temp.setType((Integer) value);
			break;
		case 2:
			float f = Float.valueOf(s);
			if (f < 100)
				f = 100;
			else if (f > 9999.0)
				f = 9999.0f;
			temp.setPrice(f);
			break;
		case 3:
			temp.setDescription(s);
			break;
		}

		changed.add(temp);
	}

	public void save() {
		RoomManager.getRoomManager().update(changed);
	}

	public void setEditable(boolean selected) {
		isEditable = selected;
	}
}
