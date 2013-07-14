package hotelmanager.util.designpatterns.observer;

/**
 * Observer in the design patterns <b>Observer</b>.
 */
public interface Observer {
	/**
	 * Called when subject notifies observers. Observers will be refreshed.
	 * 
	 * @param subject
	 *            , the subject that instance of this observes.
	 * @return null
	 */
	public void update(Subject subject);
}
