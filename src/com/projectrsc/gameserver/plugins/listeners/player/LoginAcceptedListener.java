package com.projectrsc.gameserver.plugins.listeners.player;

import com.projectrsc.gameserver.GameServer;
import com.projectrsc.gameserver.entity.Player;
import com.projectrsc.gameserver.event.EventListener;
import com.projectrsc.gameserver.event.detail.RecurringEvent;
import com.projectrsc.gameserver.event.impl.LoginAcceptedEvent;

public final class LoginAcceptedListener extends EventListener<LoginAcceptedEvent> {

	public LoginAcceptedListener() {
		super(LoginAcceptedEvent.class);
	}

	@Override
	public boolean satisfied(LoginAcceptedEvent event) {
		return true;
	}

	@Override
	public void handle(LoginAcceptedEvent event) {
		final Player player = event.getPlayer();
		
		GameServer.getInstance().getGameEngine().addEvent(new RecurringEvent(600) {
			@Override
			public void run() {
				player.getActionSender().sendInitialPlayerPositions();
			}
		});
	}

}