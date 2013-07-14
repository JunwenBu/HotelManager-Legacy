package hotelmanager.actions;

import hotelmanager.checkout.CheckOutController;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

public class CheckoutAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CheckoutAction() {
		super();

		putValue(Action.SHORT_DESCRIPTION, "��������");
		putValue(
				Action.SMALL_ICON,
				new ImageIcon(this.getClass().getResource(
						"image/checkout_16.png")));
		putValue(Action.LARGE_ICON_KEY, new ImageIcon(this.getClass()
				.getResource("image/checkout_64.png")));
	}

	public void actionPerformed(ActionEvent e) {
		new CheckOutController();
	}
}
