package hotelmanager.dbmanager;

import hotelmanager.util.GBC;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ConnectionDialog extends JDialog {
	private Properties props;

	public ConnectionDialog() {
		super((Frame) null, "Connect to DataBase...", true);

		final JTextField JDBC_Driver = new JTextField("com.mysql.jdbc.Driver",
				20);
		final JTextField url = new JTextField(
				"jdbc:mysql://localhost:3306/hotelmanager", 20);
		final JTextField user = new JTextField("root", 20);
		final JPasswordField password = new JPasswordField(20);

		JButton connect = new JButton("connect");
		connect.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				props = new Properties();

				props.setProperty("jdbc.drivers", JDBC_Driver.getText());
				props.setProperty("url", url.getText());
				props.setProperty("user", user.getText());
				props.setProperty("password", password.getText());

				System.setProperty("jdbc.drivers",
						props.getProperty("jdbc.drivers"));

				Connection connection = null;
				try {
					connection = DriverManager.getConnection(
							props.getProperty("url"), props);
				} catch (SQLException e1) {
				}
				if (connection != null) {
					ConnectionDialog.this.setVisible(false);
					try {
						props.store(
								new FileOutputStream("database.properties"),
								"--DataBase Connect infomations--");
					} catch (IOException e1) {
					}
				} else
					JOptionPane.showMessageDialog(ConnectionDialog.this,
							"��������������", "error",
							JOptionPane.ERROR_MESSAGE);
			}
		});

		setLayout(new GridBagLayout());
		add(new JLabel("Driver: "), new GBC(0, 0));
		add(JDBC_Driver, new GBC(1, 0, 3, 1));

		add(new JLabel("Database URL: "), new GBC(0, 1));
		add(url, new GBC(1, 1, 3, 1));

		add(new JLabel("User name: "), new GBC(0, 2));
		add(user, new GBC(1, 2, 3, 1));

		add(new JLabel("Password: "), new GBC(0, 3));
		add(password, new GBC(1, 3, 3, 1));

		add(connect, new GBC(2, 4));

		getRootPane().setDefaultButton(connect);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		setLocationRelativeTo(null);
		pack();
		setVisible(true);// block until press connect or closing
	}

	public Properties getInfo() {
		return props;
	}
}
