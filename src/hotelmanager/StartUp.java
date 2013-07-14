package hotelmanager;

import hotelmanager.main.MainFrame;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

public class StartUp extends SwingWorker<JFrame, String> {
	public SplashScreen splash;

	public StartUp(SplashScreen splash) {
		this.splash = splash;
	}

	@Override
	protected JFrame doInBackground() throws Exception {
		return new MainFrame(this);
	}

	public void update(String msg, int percent) {
		publish(msg);
		setProgress(percent);
	}

	@Override
	protected void process(List<String> chunks) {
		for (String chunk : chunks) {
			splash.update(chunk, getProgress());
		}
	}

	@Override
	protected void done() {
		splash.dispose();
	}

	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}
		new StartUp(new SplashScreen()).execute();
	}
}
