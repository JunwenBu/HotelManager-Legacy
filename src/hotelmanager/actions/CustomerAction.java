package hotelmanager.actions;

import hotelmanager.maintenance.CustomerMaintenance;
import hotelmanager.maintenance.CustomerTableModel;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

public class CustomerAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerAction() {
		super();

		putValue(Action.SHORT_DESCRIPTION, "��������������");

		putValue(
				Action.SMALL_ICON,
				new ImageIcon(this.getClass().getResource(
						"image/customer_16.png")));

		putValue(Action.LARGE_ICON_KEY, new ImageIcon(this.getClass()
				.getResource("image/customer_64.png")));
	}

	public void actionPerformed(ActionEvent e) {
		new CustomerMaintenance(new CustomerTableModel());
	}
}
