package com.projectrsc.gameserver.listeners;

import com.projectrsc.gameserver.entity.Player;
import com.projectrsc.gameserver.event.detail.RecurringEvent;

/**
 * An event listener that will time out a player once their last ping
 * timer has passed 15 seconds
 * 
 * @author Hikilaka
 *
 */
public final class PingTimeoutListener extends RecurringEvent {

	private final Player player;

	public PingTimeoutListener(Player player) {
		super(60000); //hence the 'try again in 60 secs' login response
		this.player = player;
	}

	@Override
	public void run() {
		if ((System.currentTimeMillis() - player.getLastPing()) >= 15000) {
			System.out.println(player.getUsername() + " has timed out!");
			player.setValid(false);
			running = false;
		}
	}

}
