package com.projectrsc.gameserver.event.detail;

import com.projectrsc.gameserver.event.Event;
import com.projectrsc.gameserver.event.EventListener;

/**
 * An event listener that will invoke <code>run</code>
 * only once, when the game engine processes this listener
 * 
 * @author Hikilaka
 *
 */
public abstract class OneTimeEvent extends EventListener<Event> {

	public OneTimeEvent() {
		super(null);
	}

	@Override
	public boolean satisfy() {
		return true;
	}
	
	@Override
	public boolean remove() {
		return true;
	}

	@Override
	public void execute() {
		run();
	}
	
	public abstract void run();

}
