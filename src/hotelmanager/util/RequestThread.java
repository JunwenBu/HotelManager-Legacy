package hotelmanager.util;

import hotelmanager.entities.Room;
import hotelmanager.status.RoomUpdateManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * Request thread asks for changing database. Server will lock and then change
 * may continue. When changed, database will unlock.
 * 
 */
public class RequestThread extends Thread {
	private RoomModel model;
	private Socket socket;

	private String serverIP;
	private int port;

	/**
	 * Construct a prepared request thread. Load socket properties from
	 * server.properties.
	 * 
	 * @param model
	 *            : RoomModel.
	 */
	public RequestThread(RoomModel model) {
		this.model = model;
		socket = new Socket();

		try {
			Properties property = new Properties();
			FileInputStream in = new FileInputStream("server.properties");
			property.load(in);
			in.close();

			serverIP = property.getProperty("serverIP");
			port = Integer.valueOf(property.getProperty("requestport"));
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

			int message = in.read();

			model.setMessage(message);

			if (message == ServerMessage.CONFIRM) {
				for (Room room : model.getUpdateRoomList())
					RoomUpdateManager.getRoomManager().updateRoomInfo(room);

				OutputStream out = socket.getOutputStream();
				out.write(ServerMessage.CONFIRM);
				out.flush();
				out.close();
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			model.setMessage(ServerMessage.NULLMESSAGE);

			in.close();
			socket.close();
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "������IP������������",
					"��������", JOptionPane.ERROR_MESSAGE);
		} catch (SocketTimeoutException e) {
			JOptionPane.showMessageDialog(null,
					"����������������������������������IP��������", "��������",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
