package hotelmanager.login;

import hotelmanager.util.GBC;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginDialog extends JDialog {
	JComboBox type;
	JTextField username;
	JPasswordField password;
	private BufferedImage background;

	public LoginDialog(final LoginController controller) {
		super((Frame) null, "HotelManager Security", true);

		try {
			background = ImageIO.read(getClass().getResource(
					"images/locked.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		type = new JComboBox(new Object[] { "����������", "����",
				"������������" });
		username = new JTextField(10);
		password = new JPasswordField(10);

		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (type.getSelectedIndex() > controller.minType)
					JOptionPane.showMessageDialog(LoginDialog.this,
							"����������������������������������!", "��������!",
							JOptionPane.ERROR_MESSAGE);
				else if (!controller.authentication())
					JOptionPane.showMessageDialog(LoginDialog.this,
							"����������!", "��������!",
							JOptionPane.ERROR_MESSAGE);
				else
					setVisible(false);
			}
		});
		ok.setToolTipText("Login");
		ok.setOpaque(false);
		getRootPane().setDefaultButton(ok);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(background, 0, 0, null);
			}
		};

		panel.setLayout(new GridBagLayout());
		panel.add(new JLabel("����:"), new GBC(0, 0));
		panel.add(type, new GBC(1, 0, 3, 1));
		panel.add(new JLabel("������:"),
				new GBC(0, 1).setFill(GridBagConstraints.BOTH));
		panel.add(username, new GBC(1, 1, 3, 1));
		panel.add(new JLabel("����:"), new GBC(0, 2));
		panel.add(password, new GBC(1, 2, 3, 1));
		panel.add(ok, new GBC(1, 3));
		panel.add(cancel, new GBC(3, 3));

		add(panel, BorderLayout.CENTER);

		setSize(background.getWidth() + 20, background.getHeight() + 40);
		setLocationRelativeTo(null);
	}
}
