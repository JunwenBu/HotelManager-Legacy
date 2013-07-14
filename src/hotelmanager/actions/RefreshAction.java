package hotelmanager.actions;

import hotelmanager.util.RoomModel;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

public class RefreshAction extends AbstractAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RefreshAction()
	{
		super();

		// putValue(Action.NAME, "房态");
		putValue(Action.SHORT_DESCRIPTION, "实时房态");
		// putValue(Action.LONG_DESCRIPTION, null);
		putValue(Action.SMALL_ICON, new ImageIcon(this.getClass().getResource(
				"image/status_16.png")));
		// putValue(Action.ACTION_COMMAND_KEY, null);
		// putValue(Action.ACCELERATOR_KEY, null);
		// putValue(Action.MNEMONIC_KEY, null);
		// putValue(Action.SELECTED_KEY, null);
		putValue(Action.LARGE_ICON_KEY, new ImageIcon(this.getClass()
				.getResource("image/status_64.png")));
	}

	public void actionPerformed(ActionEvent e)
	{
		RoomModel.getRoomModel().update();
	}
}
