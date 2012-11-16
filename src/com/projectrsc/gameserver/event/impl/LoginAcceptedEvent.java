package com.projectrsc.gameserver.event.impl;

import com.projectrsc.gameserver.entity.Player;
import com.projectrsc.gameserver.event.Event;

/**
 * An event for when a player's login attempt is successful
 */
public final class LoginAcceptedEvent extends Event {

	private final Player player;

	public LoginAcceptedEvent(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

}