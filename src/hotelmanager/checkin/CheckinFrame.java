package hotelmanager.checkin;

import hotelmanager.dbmanager.CustomerManager;
import hotelmanager.dbmanager.RoomManager;
import hotelmanager.entities.Customer;
import hotelmanager.entities.Room;
import hotelmanager.util.FunctionLibrary;
import hotelmanager.util.RequestThread;
import hotelmanager.util.RoomModel;
import hotelmanager.util.ServerMessage;
import hotelmanager.util.designpatterns.observer.Observer;
import hotelmanager.util.designpatterns.observer.Subject;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

/**
 */
public class CheckinFrame extends JFrame implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JComboBox comboBox_1, comboBox_2;
	private JTextField textField_1, textField_2, textField_3, textField_4;
	private JLabel label_6;

	private JTable table_1, table_2;
	private DefaultTableModel tablemodel_1, tablemodel_2;
	private String[] columnnames = { "��������", "��������", "����" };
	private Object[] temptableitem = new Object[3];
	private RoomModel model;

	private Float total = 0.0F;
	private int discount = 100;
	private int days = 1;

	public RoomManager theIRoomOperation;
	public CustomerManager theICustomerOperation;

	/**
	 * Create the frame
	 */
	public CheckinFrame(Room room, int type) {
		theICustomerOperation = CustomerManager.getCustomerManager();
		theIRoomOperation = RoomManager.getRoomManager();

		init(type);

		model = RoomModel.getRoomModel();
		model.attach(this);
		model.update();

		if (room != null) {
			Customer customer = theICustomerOperation.getCustomerByID(room
					.getCustomerID());

			if (customer != null) {
				textField_1.setText(customer.getName());
				discount = customer.getDiscount();
				textField_3.setText(((Float) (1.0F * discount / 100))
						.toString());
				comboBox_1.setSelectedIndex(customer.getSex());

				removeAllRooms();
				getReservedRoom();
				getVacantRoom();
				calculateTotal();

				validate();
			}
		}
	}

	/**
	 * Initialize the components in the frame.
	 * 
	 * @param type
	 * @param null
	 * @return null
	 */
	private void init(int type) {
		getContentPane().setLayout(new BorderLayout());
		final JPanel panel = new JPanel();
		panel.setLayout(null);
		getContentPane().add(panel);

		final JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 550, 145);
		panel.add(panel_1);
		SpringLayout springLayout_2 = new SpringLayout();
		panel_1.setLayout(springLayout_2);

		final JLabel label_1 = new JLabel();
		label_1.setText("��������");
		panel_1.add(label_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, label_1, 50,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, label_1, 20,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.EAST, label_1, 75,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, label_1, 20,
				SpringLayout.WEST, panel_1);

		textField_1 = new JTextField("");
		panel_1.add(textField_1);
		springLayout_2.putConstraint(SpringLayout.EAST, textField_1, 175,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, textField_1, 80,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, textField_1, 50,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, textField_1, 20,
				SpringLayout.NORTH, panel_1);

		final JLabel label_2 = new JLabel();
		label_2.setText("����");
		panel_1.add(label_2);
		springLayout_2.putConstraint(SpringLayout.EAST, label_2, 40,
				SpringLayout.EAST, textField_1);
		springLayout_2.putConstraint(SpringLayout.WEST, label_2, 10,
				SpringLayout.EAST, textField_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, label_2, 50,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, label_2, 20,
				SpringLayout.NORTH, panel_1);

		comboBox_1 = new JComboBox();
		comboBox_1.addItem("��");
		comboBox_1.addItem("��");

		panel_1.add(comboBox_1);
		springLayout_2.putConstraint(SpringLayout.EAST, comboBox_1, 270,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, comboBox_1, 220,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, comboBox_1, 50,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, comboBox_1, 20,
				SpringLayout.NORTH, panel_1);

		final JLabel label_3 = new JLabel();
		label_3.setText("����");
		panel_1.add(label_3);
		springLayout_2.putConstraint(SpringLayout.EAST, label_3, 215,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, label_3, 185,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, label_3, 130,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, label_3, 100,
				SpringLayout.NORTH, panel_1);

		final SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(1,
				1, 100, 1);
		JSpinner spinner_1 = new JSpinner(spinnerNumberModel);
		panel_1.add(spinner_1);
		springLayout_2.putConstraint(SpringLayout.EAST, spinner_1, 270,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, spinner_1, 220,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, spinner_1, 130,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, spinner_1, 100,
				SpringLayout.NORTH, panel_1);

		final JLabel label_4 = new JLabel();
		label_4.setText("����");
		panel_1.add(label_4);
		springLayout_2.putConstraint(SpringLayout.EAST, label_4, 310,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, label_4, 280,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, label_4, 130,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, label_4, 100,
				SpringLayout.NORTH, panel_1);

		textField_3 = new JTextField("");
		textField_3.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textField_3.setEditable(false);
		textField_3.setText("1.0");
		panel_1.add(textField_3);
		springLayout_2.putConstraint(SpringLayout.EAST, textField_3, 365,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, textField_3, 315,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, textField_3, 130,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, textField_3, 100,
				SpringLayout.NORTH, panel_1);

		final JLabel label_5 = new JLabel();
		label_5.setText("����");
		panel_1.add(label_5);
		springLayout_2.putConstraint(SpringLayout.EAST, label_5, 405,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, label_5, 375,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, label_5, 130,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, label_5, 100,
				SpringLayout.NORTH, panel_1);

		textField_2 = new JTextField("");
		textField_2.setEditable(false);
		textField_2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textField_2.setText(((Float) (total * discount / 100)).toString());
		panel_1.add(textField_2);

		springLayout_2.putConstraint(SpringLayout.EAST, textField_2, 525,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, textField_2, 410,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, textField_2, 130,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, textField_2, 100,
				SpringLayout.NORTH, panel_1);

		final JLabel label_9 = new JLabel();
		label_9.setText("��������");
		panel_1.add(label_9);

		springLayout_2.putConstraint(SpringLayout.SOUTH, label_9, 90,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, label_9, 60,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.EAST, label_9, 75,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, label_9, 20,
				SpringLayout.WEST, panel_1);

		final DateChooserJButton datechooser_1 = new DateChooserJButton();

		panel_1.add(datechooser_1);
		springLayout_2.putConstraint(SpringLayout.EAST, datechooser_1, 265,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, datechooser_1, 80,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, datechooser_1, 90,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, datechooser_1, 60,
				SpringLayout.NORTH, panel_1);

		final JLabel label_11 = new JLabel();
		label_11.setText("��������");
		panel_1.add(label_11);

		springLayout_2.putConstraint(SpringLayout.EAST, label_11, 335,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, label_11, 280,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, label_11, 90,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, label_11, 60,
				SpringLayout.NORTH, panel_1);

		final DateChooserJButton datechooser_2 = new DateChooserJButton(
				FunctionLibrary.getLaterDate(new Date()));

		panel_1.add(datechooser_2);
		springLayout_2.putConstraint(SpringLayout.EAST, datechooser_2, 525,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, datechooser_2, 340,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, datechooser_2, 90,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, datechooser_2, 60,
				SpringLayout.NORTH, panel_1);

		final JLabel label_7 = new JLabel();
		label_7.setText("��������");
		panel_1.add(label_7);
		springLayout_2.putConstraint(SpringLayout.EAST, label_7, 335,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, label_7, 280,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, label_7, 50,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, label_7, 20,
				SpringLayout.NORTH, panel_1);

		textField_4 = new JTextField("");
		panel_1.add(textField_4);
		springLayout_2.putConstraint(SpringLayout.EAST, textField_4, 525,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, textField_4, 340,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, textField_4, 50,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, textField_4, 20,
				SpringLayout.NORTH, panel_1);

		final JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 150, 550, 210);
		SpringLayout springLayout_3 = new SpringLayout();
		panel_2.setLayout(springLayout_3);
		panel.add(panel_2);

		final JTabbedPane tabbedPane_1 = new JTabbedPane();
		panel_2.add(tabbedPane_1);
		springLayout_3.putConstraint(SpringLayout.EAST, tabbedPane_1, 225,
				SpringLayout.WEST, panel_2);

		springLayout_3.putConstraint(SpringLayout.WEST, tabbedPane_1, 15,
				SpringLayout.WEST, panel_2);
		springLayout_3.putConstraint(SpringLayout.SOUTH, tabbedPane_1, 200,
				SpringLayout.NORTH, panel_2);
		springLayout_3.putConstraint(SpringLayout.NORTH, tabbedPane_1, 0,
				SpringLayout.NORTH, panel_2);

		final JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane_1.addTab("��������", null, scrollPane_1, null);

		final JPanel panel_2_1 = new JPanel();
		SpringLayout springLayout_1 = new SpringLayout();
		panel_2_1.setLayout(springLayout_1);
		panel_2.add(panel_2_1);
		springLayout_3.putConstraint(SpringLayout.SOUTH, panel_2_1, 200,
				SpringLayout.NORTH, tabbedPane_1);
		springLayout_3.putConstraint(SpringLayout.NORTH, panel_2_1, 0,
				SpringLayout.NORTH, tabbedPane_1);

		final JButton button_1 = new JButton();
		button_1.setIcon(new ImageIcon(getClass()
				.getResource("image/right.png")));
		button_1.setName("button_1");
		panel_2_1.add(button_1);
		springLayout_1.putConstraint(SpringLayout.SOUTH, button_1, 110,
				SpringLayout.NORTH, panel_2_1);
		springLayout_1.putConstraint(SpringLayout.NORTH, button_1, 60,
				SpringLayout.NORTH, panel_2_1);

		final JButton button_2 = new JButton();
		button_2.setName("button_2");
		button_2.setIcon(new ImageIcon(getClass().getResource("image/left.png")));
		panel_2_1.add(button_2);
		springLayout_1.putConstraint(SpringLayout.EAST, button_1, 50,
				SpringLayout.WEST, button_2);

		springLayout_1.putConstraint(SpringLayout.WEST, button_1, 0,
				SpringLayout.WEST, button_2);
		springLayout_1.putConstraint(SpringLayout.SOUTH, button_2, 170,
				SpringLayout.NORTH, panel_2_1);

		springLayout_1.putConstraint(SpringLayout.NORTH, button_2, 120,
				SpringLayout.NORTH, panel_2_1);
		springLayout_1.putConstraint(SpringLayout.EAST, button_2, 60,
				SpringLayout.WEST, panel_2_1);
		springLayout_1.putConstraint(SpringLayout.WEST, button_2, 10,
				SpringLayout.WEST, panel_2_1);

		final JTabbedPane tabbedPane_2 = new JTabbedPane();
		panel_2.add(tabbedPane_2);
		springLayout_3.putConstraint(SpringLayout.EAST, panel_2_1, -5,
				SpringLayout.WEST, tabbedPane_2);
		springLayout_3.putConstraint(SpringLayout.WEST, panel_2_1, 5,
				SpringLayout.EAST, tabbedPane_1);
		springLayout_3.putConstraint(SpringLayout.SOUTH, tabbedPane_2, 200,
				SpringLayout.NORTH, panel_2);

		springLayout_3.putConstraint(SpringLayout.NORTH, tabbedPane_2, 0,
				SpringLayout.NORTH, panel_2);

		springLayout_3.putConstraint(SpringLayout.EAST, tabbedPane_2, 525,
				SpringLayout.WEST, panel_2);
		springLayout_3.putConstraint(SpringLayout.WEST, tabbedPane_2, 310,
				SpringLayout.WEST, panel_2);

		final JScrollPane scrollPane_2 = new JScrollPane();
		tabbedPane_2.addTab("��������", null, scrollPane_2, null);

		comboBox_2 = new JComboBox();
		comboBox_2.addItem("��������");
		comboBox_2.addItem("��������");
		comboBox_2.setSelectedIndex(type);

		panel_1.add(comboBox_2);
		springLayout_2.putConstraint(SpringLayout.EAST, comboBox_2, 175,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, comboBox_2, 80,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, comboBox_2, 130,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, comboBox_2, 100,
				SpringLayout.NORTH, panel_1);

		final JLabel label = new JLabel();
		label.setText("����");
		panel_1.add(label);
		springLayout_2.putConstraint(SpringLayout.EAST, label, 75,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.WEST, label, 20,
				SpringLayout.WEST, panel_1);
		springLayout_2.putConstraint(SpringLayout.SOUTH, label, 130,
				SpringLayout.NORTH, panel_1);
		springLayout_2.putConstraint(SpringLayout.NORTH, label, 100,
				SpringLayout.NORTH, panel_1);

		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);

		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);

		setAlwaysOnTop(true);

		tablemodel_1 = new DefaultTableModel(null, columnnames);
		table_1.setModel(tablemodel_1);

		tablemodel_2 = new DefaultTableModel(null, columnnames);
		table_2.setModel(tablemodel_2);

		datechooser_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (datechooser_1.getDate().getTime() < new Date().getTime())
					datechooser_1.setDate(new Date());
				else if (datechooser_2.getDate().getTime() < datechooser_1
						.getDate().getTime())
					datechooser_2.setDate(FunctionLibrary
							.getLaterDate(datechooser_1.getDate()));

				days = (int) ((datechooser_2.getDate().getTime() - datechooser_1
						.getDate().getTime()) / 86400000 + 1);
				calculateTotal();
			}
		});

		datechooser_2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (datechooser_2.getDate().getTime() < datechooser_1.getDate()
						.getTime())
					datechooser_2.setDate(FunctionLibrary
							.getLaterDate(datechooser_1.getDate()));

				days = (int) ((datechooser_2.getDate().getTime() - datechooser_1
						.getDate().getTime()) / 86400000 + 1);
				calculateTotal();
			}
		});

		textField_4.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				try {
					Customer temp = getCustomer();

					if (temp != null) {
						removeAllRooms();

						getReservedRoom();
						getVacantRoom();
						calculateTotal();

						validate();
					}
				} catch (NumberFormatException e1) {
					textField_4.setText("");
					label_6.setText("��������������");
				}
			}
		});

		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (comboBox_2.getSelectedItem().toString().equals("��������")) {
					datechooser_1.setDate(new Date());
					datechooser_2.setDate(FunctionLibrary
							.getLaterDate(new Date()));
					datechooser_1.setEnabled(false);
				} else
					datechooser_1.setEnabled(true);
			}
		});

		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				int index = table_1.getSelectedRow();

				if (index > -1) {
					temptableitem[0] = table_1.getValueAt(index, 0);
					temptableitem[1] = table_1.getValueAt(index, 1);
					temptableitem[2] = table_1.getValueAt(index, 2);
					tablemodel_1.removeRow(index);
					tablemodel_2.addRow(temptableitem);
					calculateTotal();
				}
			}
		});

		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				int index = table_2.getSelectedRow();

				if (index > -1) {
					temptableitem[0] = table_2.getValueAt(index, 0);
					temptableitem[1] = table_2.getValueAt(index, 1);
					temptableitem[2] = table_2.getValueAt(index, 2);
					tablemodel_2.removeRow(index);
					tablemodel_1.addRow(temptableitem);
					calculateTotal();
				}
			}
		});

		final JPanel panel_3 = new JPanel();
		panel_3.setLayout(new BorderLayout());
		panel_3.setBounds(10, 358, 487, 32);
		panel.add(panel_3);

		label_6 = new JLabel("");
		panel_3.add(label_6);

		final JButton button_3 = new JButton("����");
		panel_3.add(button_3, BorderLayout.EAST);

		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Customer customer = new Customer();

				customer.setSex(comboBox_1.getSelectedIndex());

				if (!(textField_1.getText().equals(""))) {
					customer.setName(textField_1.getText());

					if (tablemodel_2.getRowCount() > 0) {
						if (!textField_4.getText().equals("")) {
							try {
								customer.setID(Long.valueOf(textField_4
										.getText()));
								customer.setDiscount(discount);

								theICustomerOperation.addNewCustomer(customer);
							} catch (NumberFormatException e) {
								label_6.setText("��������������������");
								return;
							}
						} else {
							label_6.setText("������������������");
							return;
						}
					} else {
						label_6.setText("������������");
						return;
					}
				} else {
					label_6.setText("����������");
					return;
				}

				model.removeUpdateRoomList();

				for (int rowCount = 0; rowCount < tablemodel_2.getRowCount(); rowCount++) {
					int id = Integer.valueOf(tablemodel_2.getValueAt(rowCount,
							0).toString());
					Room temp = new Room();

					for (Room room : model.getRoomList())
						if (room.getID() == id) {
							temp = room;
							break;
						}

					temp.setID(Integer.valueOf(tablemodel_2.getValueAt(
							rowCount, 0).toString()));
					temp.setCheckInDate(new Timestamp(datechooser_1.getDate()
							.getTime()));
					temp.setCheckOutDate(new Timestamp(datechooser_2.getDate()
							.getTime()));
					temp.setCustomerID(Long.valueOf(textField_4.getText()));
					temp.setState(2);
					model.addUpdateRoom(temp);
				}

				new RequestThread(RoomModel.getRoomModel()).start();

				boolean flag = false;

				for (int count = 0; count < 20; count++) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					if (RoomModel.getRoomModel().getMessage() == ServerMessage.CONFIRM) {
						flag = true;
						break;
					}
				}

				if (flag) {
					model.detach(CheckinFrame.this);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null,
							"����������������������������", "��������",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		if (type == 0) {
			setTitle("��������");
			datechooser_1.setEnabled(false);
		} else {
			setTitle("��������");
		}
		setBounds(100, 100, 566, 428);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void calculateTotal() {
		Float total = 0.0f;

		for (int count = 0; count < tablemodel_2.getRowCount(); count++)
			total += (Float) tablemodel_2.getValueAt(count, 2);

		textField_2.setText(((Float) (total * discount * days / 100))
				.toString());
	}

	private void removeAllRooms() {
		tablemodel_1 = new DefaultTableModel(null, columnnames);
		table_1.setModel(tablemodel_1);

		tablemodel_2 = new DefaultTableModel(null, columnnames);
		table_2.setModel(tablemodel_2);
	}

	private void getReservedRoom() {
		try {
			long ID = Long.valueOf(textField_4.getText());

			for (Room room : theIRoomOperation.getRoomsByCustomerID(ID)) {
				if (room.getState() == 1) {
					temptableitem[0] = room.getID();
					temptableitem[1] = FunctionLibrary.getTypeName(room
							.getType());
					temptableitem[2] = room.getPrice();

					tablemodel_2.addRow(temptableitem);
				}
			}
		} catch (NumberFormatException e) {
			textField_4.setText("");
			label_6.setText("��������������");
		}
	}

	private void getVacantRoom() {
		for (Room room : theIRoomOperation.getAllVailableRooms()) {
			temptableitem[0] = room.getID();
			temptableitem[1] = FunctionLibrary.getTypeName(room.getType());
			temptableitem[2] = room.getPrice();

			tablemodel_1.addRow(temptableitem);
		}
	}

	private Customer getCustomer() {
		try {
			long ID = Long.valueOf(textField_4.getText());
			Customer temp = theICustomerOperation.getCustomerByID(ID);

			if (temp != null) {
				textField_1.setText(temp.getName());
				discount = temp.getDiscount();
				textField_3.setText(((Float) (1.0F * discount / 100))
						.toString());
				comboBox_1.setSelectedIndex(temp.getSex());
			}

			return temp;
		} catch (NumberFormatException e) {
			textField_4.setText("");
			label_6.setText("��������������");
			return null;
		}

	}

	/**
	 * ��������������������������������RoomModel����������������
	 * 
	 * @see hotelmanager.util.designpatterns.observer.Observer#update(hotelmanager.util.designpatterns.observer.Subject)
	 */
	@Override
	public void update(Subject subject) {
		if (subject instanceof RoomModel) {
			removeAllRooms();

			getReservedRoom();
			getVacantRoom();
			calculateTotal();

			label_6.setText("");
			validate();
		}
	}
}
