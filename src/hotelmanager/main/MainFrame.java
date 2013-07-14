package hotelmanager.main;

import hotelmanager.StartUp;
import hotelmanager.actions.CheckinAction;
import hotelmanager.actions.CheckoutAction;
import hotelmanager.actions.CustomerAction;
import hotelmanager.actions.ExitAction;
import hotelmanager.actions.HelpAction;
import hotelmanager.actions.RefreshAction;
import hotelmanager.actions.ReportAction;
import hotelmanager.actions.ReserveAction;
import hotelmanager.entities.Employee;
import hotelmanager.login.LoginController;
import hotelmanager.maintenance.EmployeeMaintenance;
import hotelmanager.maintenance.EmployeeTableModel;
import hotelmanager.maintenance.RoomMaintenance;
import hotelmanager.maintenance.RoomTableModel;
import hotelmanager.status.RoomStatus;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	public static Employee currentUser;
	public static LoginController login;
	public static MainFrame mainFrame;

	public MainFrame(StartUp startUp) {
		login = new LoginController(startUp);
		currentUser = login.getEmployee(2);
		if (currentUser == null) {
			System.exit(0);
		}

		startUp.update("Starting to create the main frame...", 30);
		try {
			setIconImage(ImageIO.read(getClass()
					.getResource("images/hotel.png")));
		} catch (IOException e) {
		}
		setTitle("HotelManager");
		setExtendedState(Frame.MAXIMIZED_BOTH);

		startUp.update("Starting to create the menus...", 40);

		final JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		final JMenu subMenu_1 = new JMenu();
		subMenu_1.setText("��������");
		menuBar.add(subMenu_1);

		final JMenuItem menuItem_1_0 = new JMenuItem(new ReserveAction());
		menuItem_1_0.setText("��������");
		subMenu_1.add(menuItem_1_0);

		final JMenuItem menuItem_1_1 = new JMenuItem(new CheckinAction());
		menuItem_1_1.setText("��������");
		subMenu_1.add(menuItem_1_1);

		final JMenu subMenu_2 = new JMenu();
		subMenu_2.setText("��������");
		menuBar.add(subMenu_2);

		final JMenuItem menuItem_2_0 = new JMenuItem(new CheckoutAction());
		menuItem_2_0.setText("��������");
		subMenu_2.add(menuItem_2_0);

		final JMenu subMenu_3 = new JMenu();
		subMenu_3.setText("��������");
		menuBar.add(subMenu_3);

		final JMenuItem menuItem_3_0 = new JMenuItem(new RefreshAction());
		menuItem_3_0.setText("��������");
		subMenu_3.add(menuItem_3_0);

		JMenuItem menuItem_3_1 = new JMenuItem(new CustomerAction());
		menuItem_3_1.setText("����������");
		subMenu_3.add(menuItem_3_1);

		final JMenu subMenu_4 = new JMenu();
		subMenu_4.setText("��������");
		menuBar.add(subMenu_4);

		final JMenuItem menuItem_4_0 = new JMenuItem(new ReportAction(this));
		menuItem_4_0.setText("��������");
		subMenu_4.add(menuItem_4_0);

		final JMenu subMenu_5 = new JMenu();
		subMenu_5.setText("��������");
		menuBar.add(subMenu_5);

		final JMenuItem menuItem_5_0 = new JMenuItem();
		menuItem_5_0.setText("��������");
		subMenu_5.add(menuItem_5_0);
		menuItem_5_0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EmployeeMaintenance(new EmployeeTableModel());
			}
		});

		final JMenuItem menuItem_5_1 = new JMenuItem();
		menuItem_5_1.setText("��������");
		subMenu_5.add(menuItem_5_1);
		menuItem_5_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RoomMaintenance(new RoomTableModel());
			}
		});

		final JMenu subMenu_7 = new JMenu();
		subMenu_7.setText("����");
		menuBar.add(subMenu_7);

		final JMenuItem menuItem_7_0 = new JMenuItem(new HelpAction(this));
		menuItem_7_0.setText("��������");
		subMenu_7.add(menuItem_7_0);

		final JMenuItem menuItem_7_1 = new JMenuItem("����");
		menuItem_7_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this, "��17��������");
			}
		});
		subMenu_7.add(menuItem_7_1);

		startUp.update("Starting to create the toolbar...", 50);

		final ImageIcon toolbarbg = new ImageIcon(getClass().getResource(
				"images/toolbarpanel.jpg"));
		final ImageIcon bg = new ImageIcon(getClass().getResource(
				"images/bg.jpg"));

		final JToolBar toolBar = new JToolBar() {
			protected void paintComponent(Graphics g) {
				g.drawImage(toolbarbg.getImage(), 0, 0, null);

				super.paintComponent(g);
			}
		};

		toolBar.setBorderPainted(false);

		startUp.update("adding buttons to the toolbar...", 60);

		final JButton button = new AlphaButton();
		button.setAction(new RefreshAction());
		toolBar.add(button);

		final JButton button_1 = new AlphaButton();
		button_1.setAction(new ReserveAction());
		toolBar.add(button_1);

		final JButton button_2 = new AlphaButton();
		button_2.setAction(new CheckinAction());
		toolBar.add(button_2);

		final JButton button_3 = new AlphaButton();
		button_3.setAction(new CheckoutAction());
		toolBar.add(button_3);

		final JButton button_4 = new AlphaButton();
		button_4.setAction(new CustomerAction());
		toolBar.add(button_4);

		final JButton button_5 = new AlphaButton();
		button_5.setAction(new ReportAction(this));
		toolBar.add(button_5);

		final JButton button_6 = new AlphaButton();
		button_6.setAction(new HelpAction(this));
		toolBar.add(button_6);

		final JButton button_7 = new AlphaButton();
		button_7.setAction(new ExitAction(this));
		toolBar.add(button_7);

		final JDesktopPane desktopPane = new JDesktopPane() {
			@Override
			protected void paintChildren(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1.0f));
				super.paintChildren(g);
			}
		};

		desktopPane.setAutoscrolls(true);
		desktopPane.setOpaque(false);

		startUp.update("Getting room status from database...", 70);

		RoomStatus ff = new RoomStatus();
		desktopPane.add(ff);

		final JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				int width = bg.getIconWidth();
				int height = bg.getIconHeight();

				for (int i = 0; width * i < MainFrame.this.getWidth(); i++)
					for (int j = 0; height * j < MainFrame.this.getHeight(); j++)
						g.drawImage(bg.getImage(), width * i, height * j, null);

				Graphics2D g2 = (Graphics2D) g;
				g2.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 0.0f));

				super.paintComponent(g2);
			}
		};

		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());

		panel.add(desktopPane, BorderLayout.CENTER);
		panel.add(toolBar, BorderLayout.NORTH);
		getContentPane().add(panel);

		startUp.update("Completed", 100);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
