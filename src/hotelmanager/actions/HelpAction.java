package hotelmanager.actions;

import hotelmanager.main.MainFrame;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class HelpAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainFrame main;

	public HelpAction(MainFrame main) {
		this.main = main;
		putValue(Action.SHORT_DESCRIPTION, "����");
		putValue(Action.SMALL_ICON,
				new ImageIcon(this.getClass().getResource("image/help_16.png")));

		putValue(Action.LARGE_ICON_KEY, new ImageIcon(this.getClass()
				.getResource("image/help_64.png")));
	}

	public void actionPerformed(ActionEvent e) {
		try {
			Runtime.getRuntime().exec("hh HotelManager.chm");
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(main,
					"��������������������������������������");
		}
	}
}
