package com.projectrsc.gameserver.event.detail;

import com.projectrsc.gameserver.event.Event;
import com.projectrsc.gameserver.event.EventListener;

/**
 * An event listener that will invoke <code>run</code> every
 * time the delay has passed. The listener will be removed
 * from the game engine queue once <code>running</code> has been
 * set to <code>false</code>
 * 
 * @author Hikilaka
 *
 */
public abstract class RecurringEvent extends EventListener<Event> {
	
	private final int delay;
	
	private long lastRun;
	
	protected boolean running = true;
	
	public RecurringEvent(int delay) {
		super(null);
		this.lastRun = System.currentTimeMillis();
		this.delay = delay;
	}

	@Override
	public boolean satisfy() {
		long now = System.currentTimeMillis();
		return (now - lastRun) >= delay;
	}

	@Override
	public boolean remove() {
		return !running;
	}

	@Override
	public void execute() {
		run();
		lastRun = System.currentTimeMillis();
	}
	
	public abstract void run();

}
