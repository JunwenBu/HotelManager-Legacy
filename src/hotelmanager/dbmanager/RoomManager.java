package hotelmanager.dbmanager;

import hotelmanager.entities.Room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

public class RoomManager {
	private static RoomManager roomManager;
	private CachedRowSet crs;

	public static RoomManager getRoomManager() {
		if (roomManager == null) {
			roomManager = new RoomManager();
		}
		return roomManager;
	}

	private RoomManager() {
		try {
			crs = new CachedRowSetImpl();
			crs.setUrl(DataBaseManager.info.getProperty("url"));
			crs.setUsername(DataBaseManager.info.getProperty("user"));
			crs.setPassword(DataBaseManager.info.getProperty("password"));
			crs.setCommand("SELECT * FROM room");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Room> getAllVailableRooms() {
		return getRoomList("SELECT * FROM room WHERE state = 0");
	}

	public List<Room> getRoomsByCustomerID(long ID) {
		return getRoomList("SELECT * FROM room WHERE customerID = " + ID);
	}

	public List<Room> getRoomByRoomID(int ID) {
		return getRoomList("SELECT * FROM room WHERE ID = " + ID);
	}

	private List<Room> getRoomList(String sql) {
		List<Room> roomsList = new ArrayList<Room>();
		try {
			DataBaseManager db = new DataBaseManager();
			ResultSet rs = db.executeQuery(sql);
			while (rs != null && rs.next())
				roomsList.add(readData(rs));
		} catch (SQLException e) {
		}
		return roomsList;
	}

	public List<Room> getAllRooms() {
		return getRoomList("SELECT * FROM room");
	}

	private Room readData(ResultSet rs) throws SQLException {
		Room room = new Room();

		room.setID(rs.getInt("ID"));
		room.setType(rs.getInt("type"));
		room.setPrice(rs.getFloat("price"));
		room.setDescription(rs.getString("description"));
		room.setState(rs.getInt("state"));
		room.setCheckInDate(rs.getTimestamp("checkInDate"));
		room.setCheckOutDate(rs.getTimestamp("checkOutDate"));
		room.setCustomerID(rs.getLong("customerID"));

		return room;
	}

	public CachedRowSet getAll() {
		try {
			crs.execute();
		} catch (SQLException e) {
			for (Throwable t : e)
				t.printStackTrace();
			return null;
		}
		return crs;
	}

	public boolean update(Iterable<Room> list) {
		DataBaseManager db = new DataBaseManager();
		boolean flag = false;
		for (Room r : list) {
			String update = "UPDATE room SET type = " + r.getType()
					+ ", price = " + r.getPrice() + ", description = '"
					+ r.getDescription() + "', state = " + r.getState()
					+ ", checkInDate = '" + r.getCheckInDate() + "',"
					+ " checkOutDate = '" + r.getCheckOutDate()
					+ "', customerID = " + r.getCustomerID() + " WHERE ID = "
					+ r.getID();
			if (db.executeUpdate(update) == 1)
				flag = true;
		}
		return flag;
	}
}
