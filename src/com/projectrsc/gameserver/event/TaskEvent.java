package com.projectrsc.gameserver.event;

public interface TaskEvent {

	/**
	 * @return Whether or not the game engine should invoke <code>execute</code>
	 */
	public boolean satisfied();

	/**
	 * @return Whether or not the game engine should remove this listener from
	 *         it's queue
	 */
	public boolean remove();

	/**
	 * Invoked when <code>satisfy</code> returns true, when the game engine
	 * processes this listener
	 */
	public void handle();

}