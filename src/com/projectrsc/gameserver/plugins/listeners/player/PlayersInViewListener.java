package com.projectrsc.gameserver.plugins.listeners.player;

import com.projectrsc.gameserver.event.EventListener;
import com.projectrsc.gameserver.event.impl.MovementEvent;

public final class PlayersInViewListener extends EventListener<MovementEvent> {

	public PlayersInViewListener() {
		super(MovementEvent.class);
	}

	@Override
	public boolean satisfied(MovementEvent event) {
		return false;
	}

	@Override
	public void handle(MovementEvent event) {
		
	}

}
