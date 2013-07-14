package hotelmanager.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * ������������JTable����������������������������
 * 1.������������������������������
 * 2.����������������������������������������������������
 * 
 */
@SuppressWarnings("serial")
public class StyleTable extends JTable {
	/**
	 * ��������������������
	 */
	private String[] color = null;

	public StyleTable() {
		super();
	}

	public StyleTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		paintRow();
	}

	public StyleTable(Object[][] rowData, Object[] columnNames, String[] color) {
		super(rowData, columnNames);
		this.color = color;
		paintColorRow();
	}

	/**
	 * ����color����������������������������������������������������JTable������
	 * ����������������
	 * ���������������������������������������������������������
	 * �������������������
	 */
	public void paintRow() {
		TableColumnModel tcm = this.getColumnModel();
		for (int i = 0, n = tcm.getColumnCount(); i < n; i++) {
			TableColumn tc = tcm.getColumn(i);
			tc.setCellRenderer(new RowRenderer());
		}
	}

	public void paintColorRow() {
		TableColumnModel tcm = this.getColumnModel();
		for (int i = 0, n = tcm.getColumnCount(); i < n; i++) {
			TableColumn tc = tcm.getColumn(i);
			tc.setCellRenderer(new RowColorRenderer());
		}
	}

	private class RowRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable t, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			// ������������������������������������������
			if (row % 2 == 0)
				setBackground(Color.BLUE);
			else
				setBackground(Color.GREEN);

			return super.getTableCellRendererComponent(t, value, isSelected,
					hasFocus, row, column);
		}
	}

	private class RowColorRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable t, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			// ������������������������������
			if (color[row].trim().equals("E")) {
				setBackground(Color.RED);
			} else if (color[row].trim().equals("G")) {
				setBackground(Color.GRAY);
			} else if (color[row].trim().equals("A")) {
				setBackground(Color.BLUE);
			} else if (color[row].trim().equals("F")) {
				setBackground(Color.ORANGE);
			} else {
				setBackground(Color.WHITE);
			}

			return super.getTableCellRendererComponent(t, value, isSelected,
					hasFocus, row, column);
		}
	}
}
