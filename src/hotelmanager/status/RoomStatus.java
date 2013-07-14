package hotelmanager.status;

import hotelmanager.entities.Room;
import hotelmanager.main.AlphaButton;
import hotelmanager.util.ListenThread;
import hotelmanager.util.RoomModel;
import hotelmanager.util.designpatterns.observer.Observer;
import hotelmanager.util.designpatterns.observer.Subject;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class RoomStatus extends JInternalFrame implements Observer {
	private RoomModel model;

	private JPanel buttonpanel;
	private int currentState;
	private int all = 100;

	private JButton button, button_1, button_2, button_3, button_4;

	public RoomStatus() {
		super("��������", true, false, true, true);
		reshape(100, 100, 1000, 520);

		init();

		currentState = 4;

		// ��������������������������model������������������������������
		model = RoomModel.getRoomModel();
		model.attach(this);
		model.update();

		// ������������������������������������������������
		new ListenThread(model).start();
	}

	@Override
	protected void paintChildren(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));
		super.paintChildren(g);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.1f));
		super.paintComponent(g);
	}

	private void init() {
		setRootPane(new JRootPane() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 0.1f));
				super.paintComponent(g);
			}
		});

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		buttonpanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 0.1f));
				super.paintComponent(g);
			}

			@Override
			protected void paintChildren(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1.0f));
				super.paintChildren(g);
			}
		};
		buttonpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));
		buttonpanel.setPreferredSize(new Dimension(scrollPane
				.getPreferredSize().width, getHeight() - 30));
		buttonpanel.setAutoscrolls(true);
		scrollPane.setViewportView(buttonpanel);
		scrollPane.setMinimumSize(new Dimension(80, 80));

		final JToolBar toolBar = new JToolBar();

		toolBar.setOrientation(JToolBar.VERTICAL);
		toolBar.setBorderPainted(false);
		toolBar.setFloatable(false);

		button = new AlphaButton();
		button.setText("����");
		button.setIcon(new ImageIcon(getClass().getResource("image/all.png")));
		toolBar.add(button);

		button_1 = new AlphaButton();
		button_1.setText("������");
		button_1.setIcon(new ImageIcon(getClass().getResource(
				"image/vacant.png")));
		toolBar.add(button_1);

		button_2 = new AlphaButton();
		button_2.setText("������");
		button_2.setIcon(new ImageIcon(getClass().getResource(
				"image/reserved.png")));
		toolBar.add(button_2);

		button_3 = new AlphaButton();
		button_3.setText("����");
		button_3.setIcon(new ImageIcon(getClass().getResource(
				"image/occupied.png")));
		toolBar.add(button_3);

		button_4 = new AlphaButton();
		button_4.setText("����");
		button_4.setIcon(new ImageIcon(getClass()
				.getResource("image/dirty.png")));
		toolBar.add(button_4);

		getContentPane().add(toolBar, BorderLayout.EAST);

		setVisible(true);
		setOpaque(false);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentState = 4;
				refresh();
			}
		});

		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentState = 0;
				refresh();
			}
		});

		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentState = 1;
				refresh();
			}
		});

		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentState = 2;
				refresh();
			}
		});

		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentState = 3;
				refresh();
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				buttonpanel.setPreferredSize(new Dimension(scrollPane
						.getPreferredSize().width, all * 6400
						/ (buttonpanel.getWidth() / 80 * 80)));
			}
		});
	}

	/**
	 * ��������room button����
	 */
	private void refresh() {
		buttonpanel.removeAll();

		boolean flag = true;

		for (Room temp : model.getRoomList())
			if (currentState == 4) {
				buttonpanel.add(new RoomButton(temp));
				flag = false;
			} else if (temp.getState() == currentState) {
				buttonpanel.add(new RoomButton(temp));
				flag = false;
			}

		if (flag)
			buttonpanel.add(new JPanel());

		this.repaint();
		validate();
	}

	/**
	 * ����������
	 * 
	 * @see hotelmanager.util.designpatterns.observer.Observer#update(hotelmanager.util.designpatterns.observer.Subject)
	 */
	@Override
	public void update(Subject subject) {
		if (subject instanceof RoomModel) {
			RoomModel model = (RoomModel) subject;
			buttonpanel.removeAll();

			all = 0;
			int vacant = 0;
			int reserved = 0;
			int occupied = 0;
			int dirty = 0;

			for (Room temp : model.getRoomList()) {
				switch (temp.getState()) {
				case 0:
					vacant++;
					break;
				case 1:
					reserved++;
					break;
				case 2:
					occupied++;
					break;
				case 3:
					dirty++;
					break;
				default:
					break;
				}
				all++;
			}

			button.setText("����: " + all);
			button_1.setText("������: " + vacant);
			button_2.setText("������: " + reserved);
			button_3.setText("����: " + occupied);
			button_4.setText("����: " + dirty);
		}

		refresh();
		validate();

		try {
			SwingUtilities.getWindowAncestor(this).repaint();
		} catch (Exception e) {
		}
	}
}
