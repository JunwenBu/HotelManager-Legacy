package hotelmanager.maintenance;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

@SuppressWarnings("serial")
public class TypeEditor extends AbstractCellEditor implements TableCellEditor {
	private JComboBox options;

	public TypeEditor(Object[] items) {
		options = new JComboBox(items);
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		options.setSelectedIndex((Integer) value);
		return options;
	}

	@Override
	public Object getCellEditorValue() {
		return options.getSelectedIndex();
	}

}
