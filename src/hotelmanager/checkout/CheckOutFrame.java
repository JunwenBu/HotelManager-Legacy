package hotelmanager.checkout;

import hotelmanager.entities.Room;
import hotelmanager.util.GBC;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class CheckOutFrame extends JFrame {

	private JLabel customerIDField, roomIDField, nameField, roomTypeField,
			priceField, checkInDateField, checkOutDateField, liveDaysField,
			sexField, nameLabel;
	private JTextField initialCostField, actualCostField, discountField,
			balanceField, paidAccountField, oddField;
	private JScrollPane roomlistScrollPane, billlistScrollPane;
	private CheckOutController controller;
	private JButton submit;
	private float odd = 0;

	/**
	 * Create the frame
	 */
	public CheckOutFrame(CheckOutController controller) {
		JLabel customerIDLabel, sexLabel, roomIDLabel, roomTypeLabel, priceLabel, checkInDateLabel, checkOutDateLabel, liveDaysLabel, oddLabel, initialCostLabel, discountLabel, actualCostLabel, balanceLabel, paidAccountLabel, checkOutRoomLabel;
		setBounds(100, 100, 500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.controller = controller;
		final JPanel cInfPanel = new JPanel();
		cInfPanel.setLayout(new GridBagLayout());
		nameLabel = new JLabel(" ��������:  ");
		nameField = new JLabel();
		nameField.setFont(new Font("����", Font.ITALIC | Font.BOLD, 16));
		sexLabel = new JLabel("  ������  ");
		sexField = new JLabel();
		sexField.setFont(new Font("����", Font.ITALIC | Font.BOLD, 16));
		customerIDLabel = new JLabel("         ��������:  ");
		customerIDField = new JLabel();
		customerIDField.setFont(new Font("����", Font.ITALIC | Font.BOLD, 16));
		roomIDLabel = new JLabel(" ��������:  ");
		roomIDField = new JLabel();
		roomIDField.setFont(new Font("����", Font.ITALIC | Font.BOLD, 16));
		roomTypeLabel = new JLabel("       ��������:  ");
		roomTypeField = new JLabel();
		roomTypeField.setFont(new Font("����", Font.ITALIC | Font.BOLD, 16));
		priceLabel = new JLabel(" ����:  ");
		priceField = new JLabel();
		priceField.setFont(new Font("����", Font.ITALIC | Font.BOLD, 16));
		checkInDateLabel = new JLabel(" ��������:  ");
		checkInDateField = new JLabel();
		checkInDateField.setFont(new Font("����", Font.ITALIC | Font.BOLD, 16));
		checkOutDateLabel = new JLabel("       ��������:  ");
		checkOutDateField = new JLabel();
		checkOutDateField
				.setFont(new Font("����", Font.ITALIC | Font.BOLD, 16));
		liveDaysLabel = new JLabel(" ����:  ");
		liveDaysField = new JLabel();
		liveDaysField.setFont(new Font("����", Font.ITALIC | Font.BOLD, 16));

		cInfPanel.add(nameLabel, new GBC(0, 0, 2, 1));
		cInfPanel.add(nameField, new GBC(2, 0));
		cInfPanel.add(sexLabel, new GBC(3, 0));
		cInfPanel.add(sexField, new GBC(4, 0));
		cInfPanel.add(customerIDLabel, new GBC(5, 0));
		cInfPanel.add(customerIDField, new GBC(6, 0, 2, 1));
		cInfPanel.add(roomIDLabel, new GBC(0, 1));
		cInfPanel.add(roomIDField, new GBC(1, 1, 2, 1));
		cInfPanel.add(roomTypeLabel, new GBC(3, 1));
		cInfPanel.add(roomTypeField, new GBC(4, 1));
		cInfPanel.add(priceLabel, new GBC(5, 1));
		cInfPanel.add(priceField, new GBC(6, 1));
		cInfPanel.add(checkInDateLabel, new GBC(0, 2));
		cInfPanel.add(checkInDateField, new GBC(1, 2, 2, 1));
		cInfPanel.add(checkOutDateLabel, new GBC(3, 2));
		cInfPanel.add(checkOutDateField, new GBC(4, 2, 2, 1));
		cInfPanel.add(liveDaysLabel, new GBC(6, 2));
		cInfPanel.add(liveDaysField, new GBC(7, 2));

		Border etched = BorderFactory.createEtchedBorder();
		Border titled = BorderFactory.createTitledBorder(etched, "��������");
		cInfPanel.setBorder(titled);

		final JPanel accountInfPanel = new JPanel();
		accountInfPanel.setLayout(new GridBagLayout());
		initialCostLabel = new JLabel("��������:");
		initialCostField = new JTextField(9);
		initialCostField.setEditable(false);
		discountLabel = new JLabel("������");
		discountField = new JTextField(8);
		discountField.setEditable(false);
		actualCostLabel = new JLabel("��������:");
		actualCostField = new JTextField(8);
		actualCostField.setEditable(false);
		balanceLabel = new JLabel("��������:");
		balanceField = new JTextField(9);
		balanceField.setEditable(false);
		paidAccountLabel = new JLabel("��������:");
		paidAccountField = new JTextField("", 8);
		oddLabel = new JLabel("����:");
		oddField = new JTextField(8);
		oddField.setEditable(false);

		accountInfPanel.add(initialCostLabel, new GBC(0, 0));
		accountInfPanel.add(initialCostField, new GBC(1, 0));
		accountInfPanel.add(discountLabel, new GBC(2, 0));
		accountInfPanel.add(discountField, new GBC(3, 0));
		accountInfPanel.add(actualCostLabel, new GBC(4, 0));
		accountInfPanel.add(actualCostField, new GBC(5, 0));
		accountInfPanel.add(balanceLabel, new GBC(0, 1));
		accountInfPanel.add(balanceField, new GBC(1, 1));
		accountInfPanel.add(paidAccountLabel, new GBC(2, 1));
		accountInfPanel.add(paidAccountField, new GBC(3, 1));
		accountInfPanel.add(oddLabel, new GBC(4, 1));
		accountInfPanel.add(oddField, new GBC(5, 1));

		submit = new JButton("��������");
		accountInfPanel.add(submit, new GBC(5, 2));

		Border etched2 = BorderFactory.createEtchedBorder();
		Border titled2 = BorderFactory.createTitledBorder(etched2, "��������");
		accountInfPanel.setBorder(titled2);

		final JPanel listPanel = new JPanel();
		listPanel.setLayout(new GridLayout(0, 2));
		listPanel.setPreferredSize(new Dimension(440, 250));

		final JPanel roomListPanel = new JPanel();
		roomListPanel.setMinimumSize(new Dimension(180, 50));
		roomListPanel.setLayout(new BorderLayout());
		roomListPanel.setPreferredSize(new Dimension(200, 0));
		listPanel.add(roomListPanel);

		checkOutRoomLabel = new JLabel("   ��������");
		roomListPanel.add(checkOutRoomLabel, BorderLayout.NORTH);

		roomlistScrollPane = new JScrollPane();
		roomlistScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		roomlistScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		roomListPanel.add(roomlistScrollPane, BorderLayout.CENTER);
		final JPanel billListPanel = new JPanel();
		billListPanel.setLayout(new BorderLayout());
		billListPanel.setPreferredSize(new Dimension(200, 120));
		listPanel.add(billListPanel);

		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		billListPanel.add(tabbedPane, BorderLayout.CENTER);
		billlistScrollPane = new JScrollPane();
		tabbedPane.addTab("��������", null, billlistScrollPane, null);
		listPanel.add(billListPanel);

		Box boxH = Box.createVerticalBox();
		boxH.add(cInfPanel);
		boxH.add(accountInfPanel);
		boxH.add(listPanel);
		add(boxH);
		setVisible(true);
		setTexts();
		setActionListeners();
		setTitle("��������");
	}

	/**
	 * ������������������������������������������
	 */
	public void setTexts() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		nameField.setText((controller.customer.getName()));
		sexField.setText(controller.customer.getSex() == 0 ? "��" : "��");
		customerIDField.setText(Long.toString(controller.customer.getID()));
		roomIDField.setText(Integer.toString(controller.list.get(0).getID()));
		roomTypeField.setText(Integer
				.toString(controller.list.get(0).getType()));
		priceField.setText(Float.toString(controller.list.get(0).getPrice()));
		checkInDateField.setText(formatter.format(controller.list.get(0)
				.getCheckInDate()));
		checkOutDateField.setText(formatter.format(new Date()));

		liveDaysField
				.setText(Long.toString(((new Date()).getTime() - (controller.list
						.get(0).getCheckInDate()).getTime())
						/ (1000 * 3600 * 24) + 1));
		float initialcost = controller.getTotalAccount();
		int discount = controller.customer.getDiscount();
		initialCostField.setText(Float.toString(initialcost));
		discountField.setText(Integer.toString(discount));
		actualCostField.setText(Float.toString(initialcost * discount / 100));
		balanceField.setText(Float.toString(controller.customer.getBalance()));
		oddField.setText(Float.toString(Float.parseFloat(balanceField.getText())
				- Float.parseFloat(actualCostField.getText())));

		controller.setRoomsList(roomlistScrollPane);
		controller.setBillsList(billlistScrollPane);
		odd = Float.parseFloat(balanceField.getText())
				- Float.parseFloat(actualCostField.getText());
	}

	/**
	 * ����room������������������������������������������
	 * 
	 * @param r
	 */
	public void setRoomInf(Room r) {
		roomIDField.setText(Integer.toString(r.getID()));
		roomTypeField.setText(Integer.toString(r.getType()));
		priceField.setText(Float.toString(r.getPrice()));
	}

	private void setActionListeners() {

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				float oddment = Float.valueOf(oddField.getText());
				if (oddment < 0) {
					JOptionPane.showMessageDialog(null, "������������������",
							"����", JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						Float.valueOf(paidAccountField.getText());
						controller.submit();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "����������",
								"����", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		Document document = paidAccountField.getDocument();
		document.addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				try {
					if (paidAccountField.getText() != null
							&& paidAccountField.getText() != "")
						oddField.setText(Float.toString(odd
								+ Float.valueOf(paidAccountField.getText())));
					else {
						oddField.setText(Float.toString(odd));
					}
				} catch (Exception e1) {
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					if (paidAccountField.getText() != null
							&& paidAccountField.getText() != "")
						oddField.setText(Float.toString(odd
								+ Float.valueOf(paidAccountField.getText())));
					else {
						oddField.setText(Float.toString(odd));
					}
				} catch (Exception e1) {
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					if (paidAccountField.getText() != null
							&& paidAccountField.getText() != "")
						oddField.setText(Float.toString(odd
								+ Float.valueOf(paidAccountField.getText())));
					else {
						oddField.setText(Float.toString(odd));
					}
				} catch (Exception e1) {
					if (paidAccountField.getText() == null
							|| paidAccountField.getText() == "") {
						oddField.setText(Float.toString(odd));
					}
				}
			}

		});
	}

}
