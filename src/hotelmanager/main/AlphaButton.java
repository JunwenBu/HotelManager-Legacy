package hotelmanager.main;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class AlphaButton extends JButton {

	public AlphaButton() {
		super();
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				1.0f));

		super.paintComponent(g2);
	}
}
