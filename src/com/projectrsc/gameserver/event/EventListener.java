package com.projectrsc.gameserver.event;

/**
 * @author Hikilaka
 * @param <T>
 *            The type of event this listener is for
 */
public abstract class EventListener<T extends Event> {

	private final Class<T> classType;

	public EventListener(Class<T> classType) {
		this.classType = classType;
	}

	/**
	 * @return Whether or not the game engine should invoke <code>execute</code>
	 */
	public abstract boolean satisfied(T event);

	/**
	 * Invoked when <code>satisfy</code> returns true, when the game engine
	 * processes this listener
	 */
	public abstract void handle(T event);

	public Class<T> getClassType() {
		return classType;
	}

}