package com.projectrsc.gameserver.event;

/**
 * 
 * @author Hikilaka
 *
 * @param <T> The type of event this listener is for
 */
public abstract class EventListener<T extends Event> {

	protected final T event;

	public EventListener(T event) {
		this.event = event;
	}

	/**
	 * @return Whether or not the game engine should
	 * invoke <code>execute</code>
	 */
	public abstract boolean satisfy();

	/**
	 * @return Whether or not the game engine should
	 * remove this listener from it's queue
	 */
	public abstract boolean remove();

	/**
	 * Invoked when <code>satisfy</code> returns true, when the
	 * game engine processes this listener
	 */
	public abstract void execute();

}