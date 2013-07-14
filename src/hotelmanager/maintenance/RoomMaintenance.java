package hotelmanager.maintenance;

import hotelmanager.dbmanager.DataBaseManager;
import hotelmanager.main.MainFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

@SuppressWarnings("serial")
public class RoomMaintenance extends JFrame {
	JCheckBox edit;
	private JButton ok;
	private JButton cancel;

	public RoomMaintenance(final RoomTableModel controller) {
		JTable table = new JTable(controller);
		TableColumn sex = table.getColumnModel().getColumn(1);
		sex.setCellEditor(new TypeEditor(new String[] { "������", "����������",
				"����������", "������", "������" }));
		table.setRowHeight(20);

		edit = new JCheckBox("����");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (edit.isSelected() && MainFrame.currentUser.getType() > 0) {
					edit.setSelected(false);
					if (JOptionPane
							.showConfirmDialog(
									RoomMaintenance.this,
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
					JOptionPane.showMessageDialog(RoomMaintenance.this,
							"�������� �������� ��������");
			}
		});

		cancel = new JButton("��������");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RoomMaintenance.this.dispose();
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

	public static void main(String[] args) {
		new DataBaseManager();
		new RoomMaintenance(new RoomTableModel());
	}
}
