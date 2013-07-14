package hotelmanager.checkout;

import hotelmanager.dbmanager.BillManager;
import hotelmanager.dbmanager.CustomerManager;
import hotelmanager.dbmanager.RoomManager;
import hotelmanager.entities.Bill;
import hotelmanager.entities.Customer;
import hotelmanager.entities.Room;
import hotelmanager.main.MainFrame;
import hotelmanager.util.RequestThread;
import hotelmanager.util.RoomModel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class CheckOutController {
	RoomManager roommanager;
	BillManager billmanager;
	CustomerManager cmanager;

	ArrayList<Room> list;
	Customer customer;
	CheckOutFrame form;

	private int way = 0;// 0����������ID��1����������ID

	public CheckOutController() {
		roommanager = RoomManager.getRoomManager();
		cmanager = CustomerManager.getCustomerManager();
		billmanager = BillManager.getBillManager();
		list = new ArrayList<Room>();
		int roomID = 0;
		long customerID = 0;
		JPanel panel = new JPanel();
		final JTextField textField = new JTextField(5);
		textField.setEditable(true);
		textField.setEnabled(false);
		final JLabel label = new JLabel(" ����ID��");
		final JRadioButton customer = new JRadioButton("��������ID");
		final JRadioButton room = new JRadioButton("��������ID");
		ButtonGroup group = new ButtonGroup();
		group.add(customer);
		group.add(room);
		panel.setLayout(new GridLayout(2, 2));
		panel.add(label);
		panel.add(textField);
		panel.add(room);
		panel.add(customer);
		customer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label.setText(" ����ID��");
				textField.setEnabled(true);
				way = 0;
			}
		});
		room.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label.setText(" ����ID��");
				textField.setEnabled(true);
				way = 1;
			}
		});
		int result = JOptionPane.showConfirmDialog(null, panel, "������������",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			if (way == 1) {
				try {
					roomID = Integer.valueOf(textField.getText());
					list = (ArrayList<Room>) roommanager
							.getRoomByRoomID(roomID);
					if (list == null || list.size() == 0) {
						JOptionPane.showMessageDialog(null, "����ID��������!",
								"����", JOptionPane.INFORMATION_MESSAGE);
					} else if (list.get(0).getState() != 2) {
						JOptionPane.showMessageDialog(null,
								"������������������!", "����",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						this.customer = cmanager.getCustomerByID(list.get(0)
								.getCustomerID());
						showCheckOutForm();
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "������������", "����",
							JOptionPane.WARNING_MESSAGE);
				}
			} else {
				try {
					customerID = Long.valueOf(textField.getText());
					list = (ArrayList<Room>) roommanager
							.getRoomsByCustomerID(customerID);
					if (list == null || list.size() == 0) {
						JOptionPane.showMessageDialog(null,
								"��������������������������", "����",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						this.customer = cmanager.getCustomerByID(list.get(0)
								.getCustomerID());
						showCheckOutForm();
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "������������", "����",
							JOptionPane.WARNING_MESSAGE);
				}
			}

		} else {
		}

	}

	public CheckOutController(List<Room> roomList) {
		list = (ArrayList<Room>) roomList;
		// ��������������������������������
		Iterator<Room> iter = roomList.iterator();
		if (iter.hasNext()) {
			Room first = iter.next();
			while (iter.hasNext()) {
				if (first.getCustomerID() != iter.next().getCustomerID()) {
					JOptionPane.showMessageDialog(null,
							"��������������������������������", "���� ",
							JOptionPane.INFORMATION_MESSAGE);
					list.clear();
					list.add(first);
				}
			}
		}

		roommanager = RoomManager.getRoomManager();
		billmanager = BillManager.getBillManager();
		cmanager = CustomerManager.getCustomerManager();

		customer = cmanager.getCustomerByID(list.get(0).getCustomerID());
		list = (ArrayList<Room>) roomList;
		showCheckOutForm();
	}

	public void showCheckOutForm() {
		if (form == null) {
			form = new CheckOutFrame(this);
			form.setVisible(true);
		} else {
			form.setTexts();
			form.setVisible(true);
		}
	}

	public void submit() {
		/**
		 * ��������
		 */
		List<Bill> bills = new ArrayList<Bill>();

		Iterator<Room> iter = list.iterator();
		while (iter.hasNext()) {
			Room r = iter.next();
			Bill b = new Bill(r);

			b.setEmployeeID(MainFrame.currentUser.getID());
			b.setAmount(r.getPrice() * customer.getDiscount() / 100);
			b.setID(System.currentTimeMillis());

			bills.add(b);
			r.setState(3);
			r.setCustomerID(0);
		}

		customer.setBalance(0);
		List<Customer> cs = new ArrayList<Customer>();
		cs.add(customer);
		cmanager.update(cs);

		billmanager.addNewBill(bills);

		RoomModel.getRoomModel().setUpdateRoom(list);
		new RequestThread(RoomModel.getRoomModel()).start();

		JOptionPane.showMessageDialog(null, "���� " + customer.getName()
				+ "����������", "����", JOptionPane.INFORMATION_MESSAGE);

		form.dispose();
	}

	public float getTotalAccount() {
		float account = 0;
		Iterator<Room> iter = list.iterator();
		while (iter.hasNext()) {
			Room r = iter.next();
			long to = 0, from = 0;
			to = System.currentTimeMillis();
			from = r.getCheckInDate().getTime();
			account += (r.getPrice() * ((to - from) / (1000 * 60 * 60 * 24) + 1));
		}
		return account;
	}

	/**
	 * ����������������������
	 * 
	 * @param s
	 *            ������������ ����������
	 */
	public void setRoomsList(JScrollPane s) {
		if (list == null) {
			JOptionPane.showMessageDialog(null, "��������������������");
			return;
		}
		String roomNames[] = new String[list.size()];
		for (int i = 0; i < roomNames.length; i++) {
			roomNames[i] = "���� " + list.get(i).getID();
		}
		final JList rooms = new JList(roomNames);
		rooms.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		s.setViewportView(rooms);
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					int index = rooms.locationToIndex(e.getPoint());
					Room r = list.get(index);
					form.setRoomInf(r);
				}
			}
		};
		rooms.addMouseListener(mouseListener);
	}

	/**
	 * ��������s��������������������������
	 * 
	 * @param s
	 */
	public void setBillsList(JScrollPane s) {
		ArrayList<Bill> bills = new ArrayList<Bill>();
		List<List<Bill>> previousBills = billmanager.getBills(
				System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 30,
				System.currentTimeMillis(), list.get(0).getCustomerID(), 1);
		if (previousBills == null
				|| (previousBills.size() == 1 && previousBills.get(0) == null)) {
		} else
			for (int i = 0; i < previousBills.size(); i++) {
				for (int j = 0; j < previousBills.get(i).size(); j++) {
					bills.add(previousBills.get(i).get(j));
				}
			}
		Object[][] playerInfo;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		if (bills != null) {
			playerInfo = new Object[bills.size()][6];
			for (int i = 0; i < bills.size(); i++) {
				playerInfo[i][0] = i + 1;
				playerInfo[i][1] = bills.get(i).getRoomID();
				playerInfo[i][2] = bills.get(i).getAmount();
				playerInfo[i][3] = formatter.format((roommanager
						.getRoomsByCustomerID(bills.get(i).getCustomerID()))
						.get(0).getCheckInDate());
				playerInfo[i][4] = formatter.format(new Date(bills.get(i)
						.getID()));
				playerInfo[i][5] = bills.get(i).getRoomType();
			}
		} else
			playerInfo = null;
		String[] Names = { "����", "������", "��������", "��������",
				"��������", "��������" };
		if (playerInfo != null) {
			JTable table = new JTable(playerInfo, Names);
			table.setEnabled(false);
			table.setSize(200, 150);
			s.setViewportView(table);
		}
	}
}
