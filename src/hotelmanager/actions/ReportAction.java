package hotelmanager.actions;

import hotelmanager.chart.ReportController;
import hotelmanager.main.MainFrame;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ReportAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MainFrame main;

	public ReportAction(MainFrame main) {
		super();
		this.main = main;
		putValue(Action.SHORT_DESCRIPTION, "��������");
		putValue(Action.SMALL_ICON,
				new ImageIcon(this.getClass()
						.getResource("image/report_16.png")));
		putValue(Action.LARGE_ICON_KEY, new ImageIcon(this.getClass()
				.getResource("image/report_64.png")));
	}

	public void actionPerformed(ActionEvent e) {
		if (MainFrame.currentUser.getType() > 1) {
			if (JOptionPane.showConfirmDialog(main,
					"��������������������������������������������������",
					"HotelManager Security", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				if (MainFrame.login.getEmployee(1) != null)
					new ReportController(main);
			}
		} else
			new ReportController(main);
	}
}
