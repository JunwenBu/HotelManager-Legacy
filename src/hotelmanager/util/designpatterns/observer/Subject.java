package hotelmanager.util.designpatterns.observer;

import java.util.LinkedList;
import java.util.List;

/**
 * Subject in the design pattern <B>Observer</B>.<br>
 * <br>
 * This class offers {@link #attach} and {@link #detach} method for Observer.<br>
 * <br>
 * Method {@link #notifyObservers} is offer to meet the needs of notifying
 * observers when this subject changes.
 * 
 */
public abstract class Subject {
	/**
	 * List of Observer.
	 */
	private List<Observer> observers;

	/**
	 * Construct a subject.
	 */
	public Subject() {
		observers = new LinkedList<Observer>();
	}

	/**
	 * Attach observer.
	 * 
	 * @param observer
	 *            : Observer that observes this subject.
	 * @return true if observer is added to {@link #observers}.
	 */
	public boolean attach(Observer observer) {
		return observers.add(observer);
	}

	/**
	 * Detach observer.
	 * 
	 * @param observer
	 *            : Observer that observes this subject.
	 * @return true if if observer is removed from {@link #observers}.
	 */
	public boolean detach(Observer observer) {
		return observers.remove(observer);
	}

	/**
	 * Notify all the observers observes this subject.
	 * 
	 * @param null
	 */
	public void notifyObservers() {
		for (Observer observer : observers)
			observer.update(this);
	}
}
