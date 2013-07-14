package hotelmanager.maintenance;

import hotelmanager.main.MainFrame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public class EmployeeMaintenance extends JFrame {
	JCheckBox edit;
	private JButton ok;
	private JButton cancel;

	public EmployeeMaintenance(final EmployeeTableModel controller) {
		JTable table = new JTable(controller);
		TableColumnModel cm = table.getColumnModel();

		TableColumn type = cm.getColumn(1);
		type.setCellEditor(new TypeEditor(new String[] { "����������", "����",
				"������������" }));

		TableColumn password = cm.getColumn(2);
		password.setCellRenderer(new TableCellRenderer() {
			private JPasswordField password = new JPasswordField("12345678");

			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				return password;
			}
		});
		password.setCellEditor(new DefaultCellEditor(new JPasswordField()));

		TableColumn sex = cm.getColumn(4);
		sex.setCellEditor(new TypeEditor(new String[] { "��", "��" }));
		table.setRowHeight(20);

		edit = new JCheckBox("����");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (edit.isSelected() && MainFrame.currentUser.getType() > 0) {
					edit.setSelected(false);
					if (JOptionPane
							.showConfirmDialog(
									EmployeeMaintenance.this,
									"����\"����������\"������������������������������������������",
									"HotelManager Security",
									JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION
							&& MainFrame.login.getEmployee(0) != null)
						edit.setSelected(true);
				}

				controller.setEditable(edit.isSelected());
			}
		});

		ok = new JButton("��������");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (edit.isSelected())
					controller.save();
				else
					JOptionPane.showMessageDialog(EmployeeMaintenance.this,
							"�������� �������� ��������");
			}
		});

		cancel = new JButton("��������");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EmployeeMaintenance.this.dispose();
			}
		});
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(edit);
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		getRootPane().setDefaultButton(ok);

		add(new JScrollPane(table), BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}
}
