package com.projectrsc.gameserver.event.detail;

import com.projectrsc.gameserver.event.TaskEvent;

/**
 * An event listener that will invoke <code>run</code>
 * only once, when the game engine processes this listener
 * 
 * @author Hikilaka
 *
 */
public abstract class OneTimeEvent implements TaskEvent {

	@Override
	public boolean satisfied() {
		return true;
	}
	
	@Override
	public boolean remove() {
		return true;
	}

	@Override
	public void handle() {
		run();
	}
	
	public abstract void run();

}
