package com.projectrsc.gameserver.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.projectrsc.gameserver.event.Event;
import com.projectrsc.gameserver.event.EventListener;
import com.projectrsc.gameserver.model.Location;

public abstract class Entity {

	private static final Location DEFAULT_LOCATION = new Location(500, 300);

	/**
	 * A set of listeners that will handle when an event is sent to this entity
	 */
	private final Map<Class<? extends Event>, Set<EventListener<? extends Event>>> eventListeners = new HashMap<>();

	/**
	 * This entity's server index
	 */
	protected final AtomicInteger index = new AtomicInteger(0);

	/**
	 * This entity's location
	 */
	protected final AtomicReference<Location> location = new AtomicReference<>(DEFAULT_LOCATION);

	/**
	 * This entity's direction
	 */
	protected final AtomicInteger direction = new AtomicInteger(0);

	public void addEventListener(EventListener<? extends Event> listener) {
		Set<EventListener<? extends Event>> listeners = eventListeners.get(listener.getClassType());
		if (listeners == null) {
			listeners = new HashSet<>();
			eventListeners.put(listener.getClassType(), listeners);
		}
		listeners.add(listener);
	}

	public void removeEventListener(EventListener<? extends Event> listener) {
		Set<EventListener<? extends Event>> listeners = eventListeners.get(listener.getClassType());
		if (listeners != null) {
			listeners.remove(listener);
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized <T extends Event> void fireEvent(T event) {
		Set<EventListener<?>> listeners = eventListeners.get(event.getClass());
		if (listeners != null) {
			for (EventListener<? extends Event> listener : listeners) {
				EventListener<T> eventListener = (EventListener<T>) listener;
				if (eventListener.satisfied(event)) {
					eventListener.handle(event);
				}
			}
		}
	}

	public void setIndex(int index) {
		this.index.set(index);
	}

	public int getIndex() {
		return index.get();
	}

	public void setLocation(Location location) {
		this.location.set(location);
	}

	public Location getLocation() {
		return location.get();
	}

	public int getX() {
		return location.get().getX();
	}

	public int getY() {
		return location.get().getY();
	}

	public int getDirection() {
		return direction.get();
	}

	public void setDirection(int direction) {
		this.direction.set(direction);
	}

}