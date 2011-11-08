package org.denevell.android.serialisation.observable;

/**
 * Simple observer/listener pattern so
 * when SaveData is updated, the
 * activity can be updated.
 *
 */
public interface Observable {
	public void addListener(Listener l);
}
