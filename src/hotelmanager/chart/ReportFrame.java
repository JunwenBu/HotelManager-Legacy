package hotelmanager.chart;

import hotelmanager.entities.Bill;
import hotelmanager.entities.Room;
import hotelmanager.util.GBC;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class ReportFrame extends JFrame {
	JLabel conditionLabel, noteLabel;
	JComboBox conditionComboBox, roomComboBox, roomTypeComboBox,
			employeeComboBox;
	JScrollPane billListScrollPane;
	JButton print;
	JPanel conditionPanel;
	ReportController controller;

	/**
	 * Create the frame
	 * 
	 * @param chartController
	 */
	public ReportFrame(ReportController chartController) {
		setBounds(100, 100, 700, 625);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("��������");
		controller = chartController;
		conditionPanel = new JPanel();
		conditionPanel.setLayout(new GridBagLayout());
		conditionLabel = new JLabel("��������:    ");
		conditionComboBox = new JComboBox();
		print = new JButton("����");
		conditionPanel.add(conditionLabel, new GBC(0, 0));
		conditionPanel.add(conditionComboBox, new GBC(1, 0));
		conditionPanel
				.add(new JLabel(
						"                                                                                                   "),
						new GBC(6, 0));
		conditionPanel.add(print, new GBC(7, 0));
		conditionPanel.setPreferredSize(new Dimension(700, 70));
		Border etched = BorderFactory.createEtchedBorder();
		Border titled = BorderFactory.createTitledBorder(etched,
				"�������� & ����");
		conditionPanel.setBorder(titled);

		final JPanel billListPanel = new JPanel();
		billListPanel.setLayout(new BorderLayout());
		billListScrollPane = new JScrollPane();
		billListScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		billListPanel.add(billListScrollPane, BorderLayout.CENTER);
		billListPanel.setPreferredSize(new Dimension(450, 400));
		// controller.setBillsList(billListScrollPane);

		Border etched2 = BorderFactory.createEtchedBorder();
		Border titled2 = BorderFactory.createTitledBorder(etched2, "��������");
		billListPanel.setBorder(titled2);

		setLayout(new BorderLayout());
		add(conditionPanel, BorderLayout.NORTH);
		add(billListPanel, BorderLayout.CENTER);

		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (controller.table != null) {
					try {
						controller.table.print();
					} catch (PrinterException e1) {
						JOptionPane.showMessageDialog(null, "��������", "����",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}

		});
	}

	public void setComboxes(int type) {
		switch (type) {
		case 0:
			roomTypeComboBox = new JComboBox();
			List<List<Bill>> billLists = controller.getList();
			String[] names = null;
			try {
				names = new String[billLists.size() + 1];
				names[0] = "������������";
				for (int i = 1; i < names.length; i++) {
					names[i] = Room.typeToString(billLists.get(i - 1).get(0)
							.getRoomType());
				}
			} catch (Exception e) {
			}
			roomTypeComboBox.setModel(new DefaultComboBoxModel(names));
			break;
		case 1:
			roomComboBox = new JComboBox();
			List<List<Bill>> billLists2 = controller.getList();
			String[] names2 = new String[billLists2.size() + 1];
			names2[0] = "������������";
			for (int i = 1; i < names2.length; i++) {
				names2[i] = "" + billLists2.get(i - 1).get(0).getRoomID();
			}
			roomComboBox.setModel(new DefaultComboBoxModel(names2));
			break;
		case 2:
			employeeComboBox = new JComboBox();
			List<List<Bill>> billLists3 = controller.getList();
			String[] names3 = new String[billLists3.size() + 1];
			names3[0] = "������������";
			for (int i = 1; i < names3.length; i++) {
				names3[i] = billLists3.get(i - 1).get(0).getEmployeeID() + "";
			}
			employeeComboBox.setModel(new DefaultComboBoxModel(names3));
			break;
		default:
			JOptionPane.showMessageDialog(null, "comBox��������");
			break;
		}
	}

	/**
	 * ����������������������
	 * 
	 * @param type
	 *            0������������1����������2��������
	 */
	public void setSelectedComboBox(int type) {
		final int tableType = type;
		final List<List<Bill>> billLists = controller.getList();
		conditionPanel.remove(conditionComboBox);
		switch (type) {
		case 0:
			conditionComboBox = roomTypeComboBox;
			break;
		case 1:
			conditionComboBox = roomComboBox;
			break;
		case 2:
			conditionComboBox = employeeComboBox;
			break;
		default:
			break;
		}
		conditionPanel.add(conditionComboBox, new GBC(3, 0));
		conditionPanel.repaint();
		String[] temp = null;
		setActionPerformed(temp, tableType, billLists);
		conditionComboBox.addActionListener(new ActionListener() {
			String[] tableNames = null;

			public void actionPerformed(ActionEvent e) {
				setActionPerformed(tableNames, tableType, billLists);
			}

		});
	}

	private void noBillWarning() {
		JOptionPane.showMessageDialog(null, "��������������������", "����",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void setActionPerformed(String[] tableNames, int tableType,
			List<List<Bill>> billLists) {

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		int index0 = conditionComboBox.getSelectedIndex();
		if (index0 == -1)
			index0 = 0;
		Object[][] tableInfo = null;
		String[] colors = null;
		if (index0 == 0) {
			switch (tableType) {
			case 0:
				// ��������������
				tableNames = new String[4];
				tableNames[0] = "��������";
				tableNames[1] = "������";
				tableNames[2] = "��������";
				tableNames[3] = "����";
				if (billLists != null
						&& !(billLists.size() == 1 && billLists.get(0) == null)) {
					int index = 0, size = 0;
					for (int i = 0; i < billLists.size(); i++) {
						size += 3;
						for (int j = 0; j < billLists.get(i).size(); j++) {
							size++;
						}
					}

					tableInfo = new Object[size][4];
					colors = new String[size];
					for (int i = 0; i < billLists.size(); i++) {
						float amount = 0;

						Iterator<Bill> iter = billLists.get(i).iterator();
						while (iter.hasNext()) {
							amount += iter.next().getAmount();
						}
						tableInfo[index][0] = Room.typeToString(billLists
								.get(i).get(0).getRoomType());
						colors[index] = (i % 2 == 0 ? "W" : "G");
						for (int j = 0; j < billLists.get(i).size(); j++) {
							index++;
							tableInfo[index][1] = billLists.get(i).get(j)
									.getRoomID();
							tableInfo[index][2] = f.format(new Date(billLists
									.get(i).get(j).getID()));
							tableInfo[index][3] = billLists.get(i).get(j)
									.getAmount();
							colors[index] = (i % 2 == 0 ? "W" : "G");
						}
						index++;
						colors[index] = (i % 2 == 0 ? "W" : "G");
						tableInfo[index][1] = "��������";
						tableInfo[index][3] = amount;
						index += 2;
						colors[index - 1] = (i % 2 == 0 ? "W" : "G");
					}
				} else
					tableInfo = null;
				break;
			case 1:
				// ����������������������
				tableNames = new String[4];
				tableNames[0] = "������";
				tableNames[1] = "��������";
				tableNames[2] = "��������";
				tableNames[3] = "����";
				if (billLists != null
						&& !(billLists.size() == 1 && billLists.get(0) == null)) {
					int index = 0, size = 0;
					for (int i = 0; i < billLists.size(); i++) {
						size += 3;
						for (int j = 0; j < billLists.get(i).size(); j++) {
							size++;
						}
					}
					tableInfo = new Object[size][4];
					colors = new String[size];
					for (int i = 0; i < billLists.size(); i++) {
						float amount = 0;
						Iterator<Bill> iter = billLists.get(i).iterator();
						while (iter.hasNext()) {
							Bill temp = iter.next();
							amount += temp.getAmount();
						}
						tableInfo[index][0] = billLists.get(i).get(0)
								.getRoomID();
						tableInfo[index][1] = Room.typeToString(billLists
								.get(i).get(0).getRoomType());
						colors[index] = (i % 2 == 0 ? "W" : "G");
						for (int j = 0; j < billLists.get(i).size(); j++) {
							index++;
							tableInfo[index][2] = f.format(new Date(billLists
									.get(i).get(j).getID()));
							tableInfo[index][3] = billLists.get(i).get(j)
									.getAmount();
							colors[index] = (i % 2 == 0 ? "W" : "G");
						}
						index++;
						colors[index] = (i % 2 == 0 ? "W" : "G");
						tableInfo[index][1] = "����������";
						tableInfo[index][3] = amount;
						index += 2;
						colors[index - 1] = (i % 2 == 0 ? "W" : "G");
					}
				} else
					tableInfo = null;
				break;
			case 2:
				// ����������������������
				tableNames = new String[5];
				tableNames[0] = "������";
				tableNames[1] = "������";
				tableNames[2] = "��������";
				tableNames[3] = "��������";
				tableNames[4] = "����";
				if (billLists != null
						&& !(billLists.size() == 1 && billLists.get(0) == null)) {
					int index = 0, size = 0;
					for (int i = 0; i < billLists.size(); i++) {
						size += 3;
						for (int j = 0; j < billLists.get(i).size(); j++) {
							size++;
						}
					}

					tableInfo = new Object[size][5];
					colors = new String[size];
					for (int i = 0; i < billLists.size(); i++) {
						float amount = 0;
						Iterator<Bill> iter = billLists.get(i).iterator();
						while (iter.hasNext()) {
							amount += iter.next().getAmount();
						}
						tableInfo[index][0] = billLists.get(i).get(0)
								.getEmployeeID();
						colors[index] = (i % 2 == 0 ? "W" : "G");
						for (int j = 0; j < billLists.get(i).size(); j++) {
							index++;
							tableInfo[index][1] = billLists.get(i).get(j)
									.getRoomID();
							tableInfo[index][2] = Room.typeToString(billLists
									.get(i).get(j).getRoomType());
							tableInfo[index][3] = f.format(new Date(billLists
									.get(i).get(j).getID()));
							tableInfo[index][4] = billLists.get(i).get(j)
									.getAmount();
							colors[index] = (i % 2 == 0 ? "W" : "G");
						}
						index++;
						colors[index] = (i % 2 == 0 ? "W" : "G");
						tableInfo[index][3] = "��������";
						tableInfo[index][4] = amount;
						index += 2;
						colors[index - 1] = (i % 2 == 0 ? "W" : "G");
					}
				} else
					tableInfo = null;
				break;
			default:
				tableInfo = null;
				break;
			}
		} else {
			List<Bill> bills = controller.getList().get(index0 - 1);
			switch (tableType) {
			case 0:
				// ����������������
				tableNames = new String[5];
				tableNames[0] = "����";
				tableNames[1] = "��������";
				tableNames[2] = "������";
				tableNames[3] = "����";
				tableNames[4] = "��������";
				if (billLists != null
						&& !(billLists.size() == 1 && billLists.get(0) == null)) {
					float amount = 0;
					Iterator<Bill> iter = bills.iterator();
					while (iter.hasNext()) {
						amount += iter.next().getAmount();
					}
					tableInfo = new Object[bills.size() + 1][5];
					for (int i = 0; i < bills.size(); i++) {
						tableInfo[i][0] = i + 1;
						tableInfo[i][1] = Room.typeToString(bills.get(i)
								.getRoomType());
						tableInfo[i][2] = bills.get(i).getRoomID();
						tableInfo[i][3] = f.format(new Date(bills.get(i)
								.getID()));
						tableInfo[i][4] = bills.get(i).getAmount();
					}
					tableInfo[bills.size()][3] = "������";
					tableInfo[bills.size()][4] = amount;
				} else {
					noBillWarning();
					tableInfo = null;
				}
				break;
			case 1:
				tableNames = new String[5];
				tableNames[0] = "����";
				tableNames[1] = "������";
				tableNames[2] = "��������";
				tableNames[3] = "��������";
				tableNames[4] = "����";
				if (billLists != null
						&& !(billLists.size() == 1 && billLists.get(0) == null)) {
					float amount = 0;
					Iterator<Bill> iter = bills.iterator();
					while (iter.hasNext()) {
						amount += iter.next().getAmount();
					}
					tableInfo = new Object[bills.size() + 1][5];
					for (int i = 0; i < bills.size(); i++) {
						tableInfo[i][0] = i + 1;
						tableInfo[i][1] = bills.get(i).getRoomID();
						tableInfo[i][2] = Room.typeToString(bills.get(i)
								.getRoomType());
						tableInfo[i][3] = f.format(new Date(bills.get(i)
								.getID()));
						tableInfo[i][4] = bills.get(i).getAmount();
					}
					tableInfo[bills.size()][3] = "������";
					tableInfo[bills.size()][4] = amount;
				} else {
					noBillWarning();
					tableInfo = null;
				}

				break;

			case 2:
				// ��������ID
				tableNames = new String[5];
				tableNames[0] = "����";
				tableNames[1] = "������";
				tableNames[2] = "������";
				tableNames[3] = "����";
				tableNames[4] = "��������";
				if (billLists != null
						&& !(billLists.size() == 1 && billLists.get(0) == null)) {
					float amount = 0;
					Iterator<Bill> iter = bills.iterator();
					while (iter.hasNext()) {
						amount += iter.next().getAmount();
					}
					tableInfo = new Object[bills.size() + 1][5];
					for (int i = 0; i < bills.size(); i++) {
						tableInfo[i][0] = i + 1;
						tableInfo[i][1] = bills.get(i).getEmployeeID();
						tableInfo[i][2] = bills.get(i).getRoomID();
						tableInfo[i][4] = bills.get(i).getAmount();
						tableInfo[i][3] = f.format(new Date(bills.get(i)
								.getID()));
					}
					tableInfo[bills.size()][3] = "������";
					tableInfo[bills.size()][4] = amount;
				} else {
					noBillWarning();
					tableInfo = null;
				}
				break;
			default:
				break;
			}
		}
		controller.setBillList(billListScrollPane, tableInfo, tableNames,
				colors);

	}
}
