package hotelmanager.dbmanager;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JOptionPane;

public class DataBaseManager {
	public volatile static Properties info;
	public static Connection conn;

	private Connection connection;
	private Statement stat;

	public DataBaseManager() {
		if (DataBaseManager.info == null)
			DataBaseManager.getConnectionInfo();
		try {
			connection = DriverManager.getConnection(
					DataBaseManager.info.getProperty("url"),
					DataBaseManager.info);
			stat = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void finalize() throws Throwable {
		connection.close();
		super.finalize();
	}

	private static void getConnectionInfo() {
		Properties props = new Properties();
		try {
			FileInputStream in = new FileInputStream("database.properties");
			props.load(in);
			System.setProperty("jdbc.drivers",
					props.getProperty("jdbc.drivers"));
			in.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"can't load info from the file 'database.properties'!",
					"DataBaseManager", JOptionPane.ERROR_MESSAGE);

			ConnectionDialog dialog = new ConnectionDialog();
			props = dialog.getInfo();
			dialog.dispose();
		}
		info = props;
		try {
			conn = DriverManager.getConnection(
					DataBaseManager.info.getProperty("url"),
					DataBaseManager.info);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {
			rs = stat.executeQuery(sql);
		} catch (SQLException e) {
		}
		return rs;
	}

	public int executeUpdate(String sql) {
		int count = 0;
		try {
			count = stat.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}
}
