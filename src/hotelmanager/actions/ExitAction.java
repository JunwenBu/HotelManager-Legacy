package hotelmanager.actions;

import hotelmanager.main.MainFrame;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ExitAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainFrame main;

	public ExitAction(MainFrame main) {
		this.main = main;
		putValue(Action.SHORT_DESCRIPTION, "��������");
		putValue(Action.SMALL_ICON,
				new ImageIcon(this.getClass().getResource("image/exit_16.png")));
		putValue(Action.LARGE_ICON_KEY, new ImageIcon(this.getClass()
				.getResource("image/exit_64.png")));
	}

	public void actionPerformed(ActionEvent e) {
		if (JOptionPane.showConfirmDialog(main, "����������������", "Exit",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			System.exit(0);
	}
}
