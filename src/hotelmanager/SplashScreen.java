package hotelmanager;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class SplashScreen extends JWindow {
	private BufferedImage splashImage;
	private JPanel splashPanel;
	private String message = "Starting up...";
	private JProgressBar progressBar;

	public SplashScreen() {
		try {
			splashImage = ImageIO.read(getClass().getResource(
					"images/splash.png"));
		} catch (IOException e1) {
		}

		splashPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(splashImage, 0, 0, null);
				g.drawString(message, 10, getHeight() - 70);
			}
		};

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);

		splashPanel.setLayout(new BorderLayout());
		splashPanel.add(progressBar, BorderLayout.SOUTH);
		add(splashPanel);

		setSize(splashImage.getWidth(), splashImage.getHeight());
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void update(String msg, int progress) {
		message = msg;
		progressBar.setValue(progress);
		repaint();
	}
}
