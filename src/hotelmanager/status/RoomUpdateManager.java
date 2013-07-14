package hotelmanager.status;

import hotelmanager.dbmanager.DataBaseManager;
import hotelmanager.entities.Room;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoomUpdateManager {
	private static RoomUpdateManager roomManager;

	private PreparedStatement add_PS;
	private PreparedStatement deleteByID_PS;
	private PreparedStatement updateInfo_PS;

	private final static String addRoom_SQL = "INSERT INTO room VALUES"
			+ " (?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String deleteRoomByID_SQL = "DELETE FROM room WHERE ID = ?";
	private final static String updateRoomInfo_SQL = "UPDATE room SET type = ?,"
			+ " price = ?, description = ?, state = ?, checkInDate = ?,"
			+ " checkOutDate = ?, customerID = ? WHERE ID = ?";

	public static RoomUpdateManager getRoomManager() {
		if (roomManager == null)
			roomManager = new RoomUpdateManager();

		return roomManager;
	}

	private RoomUpdateManager() {
		try {
			Connection conn = DriverManager.getConnection(
					DataBaseManager.info.getProperty("url"),
					DataBaseManager.info);

			add_PS = conn.prepareStatement(addRoom_SQL);
			deleteByID_PS = conn.prepareStatement(deleteRoomByID_SQL);
			updateInfo_PS = conn.prepareStatement(updateRoomInfo_SQL);
		} catch (SQLException e) {
			for (Throwable t : e)
				t.printStackTrace();
		}
	}

	public boolean addNewRoom(Room newRoom) {
		int rowCount = 0;

		try {
			add_PS.setInt(1, newRoom.getID());
			add_PS.setInt(2, newRoom.getType());
			add_PS.setFloat(3, newRoom.getPrice());
			add_PS.setString(4, newRoom.getDescription());
			add_PS.setInt(5, newRoom.getState());
			add_PS.setTimestamp(6, newRoom.getCheckInDate());
			add_PS.setTimestamp(7, newRoom.getCheckOutDate());
			add_PS.setLong(8, newRoom.getCustomerID());

			rowCount = add_PS.executeUpdate();
		} catch (SQLException e) {
			for (Throwable t : e)
				t.printStackTrace();
			return false;
		}

		if (rowCount == 1)
			return true;
		else
			return false;
	}

	public boolean deleteRoomByID(int ID) {
		int rowCount = 0;

		try {
			deleteByID_PS.setInt(1, ID);

			rowCount = deleteByID_PS.executeUpdate();
		} catch (SQLException e) {
			for (Throwable t : e)
				t.printStackTrace();
			return false;
		}

		if (rowCount == 1)
			return true;
		else
			return false;
	}

	public boolean updateRoomInfo(Room changedRoom) {
		int rowCount = 0;

		try {
			updateInfo_PS.setInt(8, changedRoom.getID());
			updateInfo_PS.setInt(1, changedRoom.getType());
			updateInfo_PS.setFloat(2, changedRoom.getPrice());
			updateInfo_PS.setString(3, changedRoom.getDescription());
			updateInfo_PS.setInt(4, changedRoom.getState());
			updateInfo_PS.setTimestamp(5, changedRoom.getCheckInDate());
			updateInfo_PS.setTimestamp(6, changedRoom.getCheckOutDate());
			updateInfo_PS.setLong(7, changedRoom.getCustomerID());

			rowCount = updateInfo_PS.executeUpdate();
		} catch (SQLException e) {
			for (Throwable t : e)
				t.printStackTrace();

			return false;
		}

		if (rowCount == 1)
			return true;
		else
			return false;
	}
}
