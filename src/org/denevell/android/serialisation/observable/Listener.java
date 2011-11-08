package org.denevell.android.serialisation.observable;

/**
 * Simple observer/listener pattern so
 * when SaveData is updated, the
 * activity can be updated.
 */
public interface Listener {
	public void observerableUpdated(Object c);
}
