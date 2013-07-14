package hotelmanager.status;

import hotelmanager.checkin.CheckinFrame;
import hotelmanager.checkout.CheckOutController;
import hotelmanager.entities.Room;
import hotelmanager.util.FunctionLibrary;
import hotelmanager.util.RequestThread;
import hotelmanager.util.RoomModel;
import hotelmanager.util.ServerMessage;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

/**
 * RoomButton extends JButton by referring to corresponding icon according to
 * the parameter room in constructor. Different observers including check in
 * frame & check out frame will be constructed by clicking the menu item in the
 * pop up menu.
 * 
 */
@SuppressWarnings("serial")
public class RoomButton extends JButton {
	private Room room;

	/**
	 * Construct a room button. Room ID, status and type will be displayed
	 * through a icon that attach to it.
	 * 
	 * @param room
	 *            : Integer that contains room information.
	 * @param theController
	 *            : StatusController for construct new observers.
	 */
	public RoomButton(Room theRoom) {
		super();
		setPreferredSize(new Dimension(64, 64));
		setMinimumSize(new Dimension(64, 64));
		setMaximumSize(new Dimension(64, 64));

		room = theRoom;

		setText(Integer.valueOf(room.getID()).toString());
		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.CENTER);
		// setContentAreaFilled(false);

		setIcon(new ImageIcon(getClass().getResource(
				"image/" + room.getType() + "_" + room.getState() + ".png")));

		setToolTipText("���������� "
				+ FunctionLibrary.getTypeName(room.getType()));

		// add a pop up menu according to room's state.
		addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JPopupMenu menu = new JPopupMenu();
				JMenuItem item_1, item_2;

				switch (room.getState()) {
				case 0:
					item_2 = new JMenuItem("��������");
					item_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							new CheckinFrame(room, 0);
						}
					});
					menu.add(item_2);
					break;
				case 1:
					item_1 = new JMenuItem("��������");
					item_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							RoomModel model = RoomModel.getRoomModel();

							if (model.getMessage() == ServerMessage.NULLMESSAGE) {
								if (room.getState() == 3) {
									room.setState(0);
									room.setCustomerID(0);

									model.removeUpdateRoomList();
									model.addUpdateRoom(room);

									new RequestThread(RoomModel.getRoomModel())
											.start();
								}
							}
						}
					});
					menu.add(item_1);

					item_2 = new JMenuItem("��������");
					item_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							new CheckinFrame(room, 0);
						}
					});
					menu.add(item_2);
					break;
				case 2:
					item_2 = new JMenuItem("��������");
					item_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							List<Room> list = new ArrayList<Room>();
							list.add(room);
							new CheckOutController(list);
						}
					});
					menu.add(item_2);
					break;
				case 3:
					item_1 = new JMenuItem("��������");
					item_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							RoomModel model = RoomModel.getRoomModel();
							if (model.getMessage() == ServerMessage.NULLMESSAGE) {
								if (room.getState() == 3) {
									room.setState(0);

									model.removeUpdateRoomList();
									model.addUpdateRoom(room);

									new RequestThread(RoomModel.getRoomModel())
											.start();
								}
							}
						}
					});
					menu.add(item_1);
					break;
				default:
				}

				menu.show(RoomButton.this, 48, 48);
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));
		super.paintComponent(g);
	}
}