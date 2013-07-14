package hotelmanager.util;

import hotelmanager.dbmanager.RoomManager;
import hotelmanager.entities.Room;
import hotelmanager.util.designpatterns.observer.Subject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

/**
 * StatusModel comprises a list {@link #roomList} in order to store room
 * information. Corresponding getter & setter are offered.
 * 
 */
public class RoomModel extends Subject {
	/**
	 * Singleton of RoomModel.
	 */
	private static RoomModel model;
	/**
	 * A LinkedList that stored room information.
	 */
	private List<Room> roomList;

	private int message;
	private List<Room> updateRoomList;

	/**
	 * Construct an empty room model. The list {@link #roomList} will be
	 * initialized.
	 * 
	 * @see Subject#Subject()
	 */
	private RoomModel() {
		super();

		roomList = new ArrayList<Room>();
		updateRoomList = new ArrayList<Room>();
		message = ServerMessage.NULLMESSAGE;
	}

	/**
	 * @return the RoomModel {@link #model}.
	 */
	public static RoomModel getRoomModel() {
		if (model == null)
			model = new RoomModel();

		return model;
	}

	/**
	 * Update {@link #roomList}.
	 */
	public void update() {
		CachedRowSet crs = RoomManager.getRoomManager().getAll();

		roomList = new ArrayList<Room>();

		try {
			while (crs.next()) {
				Room room = new Room();

				room.setID(crs.getInt("ID"));
				room.setType(crs.getInt("type"));
				room.setPrice(crs.getFloat("price"));
				room.setDescription(crs.getString("description"));
				room.setState(crs.getInt("state"));
				room.setCheckInDate(crs.getTimestamp("checkInDate"));
				room.setCheckOutDate(crs.getTimestamp("checkOutDate"));
				room.setCustomerID(crs.getLong("customerID"));

				roomList.add(room);
			}

			notifyObservers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Getter of {@link #roomList}.
	 * 
	 * @return the roomList
	 */
	public List<Room> getRoomList() {
		return roomList;
	}

	/**
	 * @param room
	 *            add the Room to updateRoomList
	 */
	public void addUpdateRoom(Room room) {
		updateRoomList.add(room);
	}

	/**
	 * @param room
	 *            add the Room to updateRoomList
	 */
	public void setUpdateRoom(List<Room> list) {
		updateRoomList = list;
	}

	/**
	 * @param updateRoomList
	 *            the updateRoomList to set
	 */
	public void removeUpdateRoomList() {
		updateRoomList = new ArrayList<Room>();
	}

	/**
	 * @return the updateRoomList
	 */
	public List<Room> getUpdateRoomList() {
		return updateRoomList;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(int message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public int getMessage() {
		return message;
	}
}
