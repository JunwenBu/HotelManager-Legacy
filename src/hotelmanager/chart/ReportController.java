package hotelmanager.chart;

import hotelmanager.dbmanager.BillManager;
import hotelmanager.entities.Bill;
import hotelmanager.entities.Room;
import hotelmanager.main.MainFrame;
import hotelmanager.util.StyleTable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.TableOrder;

public class ReportController {
	private final SimpleDateFormat[] timeUnit = new SimpleDateFormat[] {
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy-MM"), new SimpleDateFormat("yyyy") };

	private BillManager billManager;
	private List<List<Bill>> data;

	private DefaultCategoryDataset dataset;

	private ChartFrame chartFrame;

	private ReportFrame reportFrame;

	public JTable table;

	public ReportController(MainFrame main) {
		billManager = BillManager.getBillManager();
		dataset = new DefaultCategoryDataset();
		chartFrame = new ChartFrame(this);
		reDrawChart(0);
		chartFrame.setVisible(true);
		reportFrame = new ReportFrame(this);
	}

	private void getData() {
		long beginID = chartFrame.beginDate.getDate().getTime();
		long endID = chartFrame.endDate.getDate().getTime();
		long customerID = 0;
		try {
			customerID = Long.parseLong(chartFrame.customerID.getText());
		} catch (Exception e) {
		}
		data = billManager.getBills(beginID, endID, customerID,
				chartFrame.orderBy.getSelectedIndex());
	}

	private void createDataset() {
		dataset.clear();

		for (List<Bill> temp : data) {
			String rowKey = getRowKey(temp.get(0));
			for (Bill b : temp) {
				try {
					dataset.incrementValue(b.getAmount(), rowKey,
							getColumnKey(b));
				} catch (IllegalArgumentException e) {
					dataset.addValue(b.getAmount(), rowKey, getColumnKey(b));
				}
			}
		}
	}

	private String getRowKey(Bill b) {
		switch (chartFrame.orderBy.getSelectedIndex()) {
		case 0:
			return Room.typeToString(b.getRoomType());
		case 1:
			return b.getRoomID() + "";
		case 2:
			return b.getEmployeeID() + "";
		default:
			return "";
		}
	}

	private String getColumnKey(Bill b) {
		return timeUnit[chartFrame.timeUnit.getSelectedIndex()]
				.format(new Date(b.getID()));
	}

	void reDrawChart(int selectedIndex) {
		switch (selectedIndex) {
		case 0:
			chartFrame.chartPanel.setChart(ChartFactory
					.createStackedBarChart3D("Test", "����", "������", dataset,
							PlotOrientation.VERTICAL, true, true, false));
			break;
		case 1:
			chartFrame.chartPanel.setChart(ChartFactory
					.createMultiplePieChart3D("Test", dataset,
							TableOrder.BY_COLUMN, true, true, false));
			break;
		case 2:
			chartFrame.chartPanel.setChart(ChartFactory.createLineChart3D(
					"Test", "����", "������", dataset,
					PlotOrientation.VERTICAL, true, true, false));
			break;

		default:
			break;
		}
	}

	void draw() {
		getData();
		createDataset();
		reDrawChart(0);
	}

	void showReport() {
		getData();
		switch (chartFrame.orderBy.getSelectedIndex()) {
		case 0: {
			reportFrame.setComboxes(0);
			reportFrame.setSelectedComboBox(0);
			break;
		}
		case 1: {
			reportFrame.setComboxes(1);
			reportFrame.setSelectedComboBox(1);
			break;
		}
		case 2: {
			reportFrame.setComboxes(2);
			reportFrame.setSelectedComboBox(2);
			break;
		}
		default: {
			break;
		}
		}
		reportFrame.setVisible(true);
	}

	public void setBillList(JScrollPane s, Object[][] inf,
			String[] columnNames, String[] colors) {
		if (inf != null) {
			if (colors == null) {
				table = new JTable(inf, columnNames);
				table.setSize(200, 150);
				table.setEnabled(false);
				s.setViewportView(table);
			} else {
				table = new StyleTable(inf, columnNames, colors);
				table.setSize(200, 150);
				table.setEnabled(false);
				s.setViewportView(table);
			}
		}
	}

	public List<List<Bill>> getList() {
		return data;
	}
}
