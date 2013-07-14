package hotelmanager.actions;

import hotelmanager.checkin.CheckinFrame;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

public class ReserveAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReserveAction() {
		super();
		putValue(Action.SHORT_DESCRIPTION, "��������");
		putValue(Action.SMALL_ICON,
				new ImageIcon(getClass().getResource("image/reserve_16.png")));
		putValue(Action.LARGE_ICON_KEY,
				new ImageIcon(getClass().getResource("image/reserve_64.png")));
	}

	public void actionPerformed(ActionEvent e) {
		new CheckinFrame(null, 1);
	}
}
