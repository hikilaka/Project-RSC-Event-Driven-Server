package com.projectrsc.gameserver.event.detail;

import com.projectrsc.gameserver.event.TaskEvent;

/**
 * A <code>TaskEvent</code> that will invoke <code>run</code> every
 * time the delay has passed. The task will be removed
 * from the game engine queue once <code>running</code> has been
 * set to <code>false</code>
 * 
 * @author Hikilaka
 *
 */
public abstract class RecurringEvent implements TaskEvent {
	
	private final int delay;
	
	private long lastRun;
	
	protected boolean running = true;
	
	public RecurringEvent(int delay) {
		this.lastRun = System.currentTimeMillis();
		this.delay = delay;
	}

	@Override
	public boolean satisfied() {
		long now = System.currentTimeMillis();
		return (now - lastRun) >= delay;
	}

	@Override
	public boolean remove() {
		return !running;
	}

	@Override
	public void handle() {
		run();
		lastRun = System.currentTimeMillis();
	}
	
	public abstract void run();

}
