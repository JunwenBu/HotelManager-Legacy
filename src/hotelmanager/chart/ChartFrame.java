package hotelmanager.chart;

import hotelmanager.util.GBC;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;

import com.lavantech.gui.comp.DateTimePicker;

@SuppressWarnings("serial")
public class ChartFrame extends JFrame {
	private ReportController controller;

	DateTimePicker beginDate;
	DateTimePicker endDate;
	JTextField customerID;
	JComboBox orderBy;
	JComboBox timeUnit;

	ChartPanel chartPanel;

	ChartFrame(ReportController c) {
		setTitle("����������");
		setSize(800, 640);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.controller = c;

		beginDate = new DateTimePicker(new Date(new Date().getTime() - 24 * 60
				* 60 * 1000), "yyyy-MM-dd");
		endDate = new DateTimePicker(new Date(), "yyyy-MM-dd");
		customerID = new JTextField(11);
		orderBy = new JComboBox(new Object[] { "����������", "����������",
				"����������" });
		timeUnit = new JComboBox(new Object[] { "��", "��", "��" });
		final JComboBox chartType = new JComboBox(new Object[] { "����������",
				"��������", "3D������" });
		chartType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.reDrawChart(chartType.getSelectedIndex());
			}
		});
		JButton draw = new JButton("����");
		draw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.draw();
			}
		});
		JButton report = new JButton("����");
		report.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.showReport();
			}
		});

		final JPanel reportPanel = new JPanel(new GridBagLayout());
		reportPanel.add(new JLabel("��������:"), new GBC(0, 0, 2, 1));
		reportPanel.add(beginDate, new GBC(2, 0, 3, 1));
		reportPanel.add(new JLabel("��������:"), new GBC(5, 0, 2, 1));
		reportPanel.add(endDate, new GBC(7, 0, 3, 1));

		reportPanel.add(new JLabel("����ID��:"), new GBC(0, 1, 2, 1));
		reportPanel.add(customerID, new GBC(2, 1, 3, 1));
		reportPanel.add(new JLabel("��������:"), new GBC(5, 1, 2, 1));
		reportPanel.add(orderBy, new GBC(7, 1, 2, 1));
		reportPanel.add(report, new GBC(10, 2));
		reportPanel.setBorder(BorderFactory.createTitledBorder("����"));

		final JPanel drawPanel = new JPanel(new GridBagLayout());
		drawPanel.add(new JLabel("��������:"), new GBC(0, 0, 2, 1));
		drawPanel.add(chartType, new GBC(2, 0, 2, 1));
		drawPanel.add(new JLabel("��������:"), new GBC(0, 1, 2, 1));
		drawPanel.add(timeUnit, new GBC(2, 1, 2, 1));
		drawPanel.add(draw, new GBC(4, 2));
		drawPanel.setBorder(BorderFactory.createTitledBorder("��������"));

		chartPanel = new ChartPanel(null);

		add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, reportPanel, drawPanel),
				chartPanel), BorderLayout.NORTH);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ChartFrame(null).setVisible(true);
	}

}
