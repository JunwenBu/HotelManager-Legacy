package hotelmanager.util;

import hotelmanager.main.MainFrame;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * ListenThread listens the server. When database changes, this listener will
 * call the room model to update itself.
 * 
 */
public class ListenThread extends Thread {
	private RoomModel model;
	private Socket socket;

	private String serverIP;
	private int port;

	/**
	 * Construct a prepared listen thread. Load socket properties from
	 * server.properties.
	 * 
	 * @param model
	 *            : RoomModel. When database in server changes, it will update
	 *            itself.
	 */
	public ListenThread(RoomModel model) {
		this.model = model;
		socket = new Socket();

		try {
			Properties property = new Properties();
			FileInputStream in = new FileInputStream("server.properties");
			property.load(in);
			in.close();

			serverIP = property.getProperty("serverIP");
			port = Integer.valueOf(property.getProperty("listenport"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Can not find file 'server.properties'!");
		}
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			InetSocketAddress address;

			address = new InetSocketAddress(serverIP, port);
			socket.connect(address, 0);
			InputStream in = socket.getInputStream();

			while (true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (in.read() == ServerMessage.CHANGED) {
					model.update();
					MainFrame.mainFrame.repaint();
				}

				if (socket.isClosed())
					break;
			}

			in.close();
			socket.close();
		} catch (SocketTimeoutException e) {
			JOptionPane.showMessageDialog(null,
					"����������������������������������IP��������", "��������",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
