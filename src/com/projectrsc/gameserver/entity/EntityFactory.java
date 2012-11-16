package com.projectrsc.gameserver.entity;

import java.util.Set;

import com.projectrsc.gameserver.event.Event;
import com.projectrsc.gameserver.event.EventListener;
import com.projectrsc.gameserver.plugins.PluginHandler;
import com.projectrsc.shared.network.Session;

public final class EntityFactory {

	public static Player newPlayer(Session session, String username, String password) {
		Player player = new Player(session, username, password);
		Set<EventListener<? extends Event>> playerListeners = PluginHandler.getInstance().getPlayerEventListeners();

		// add the mandatory event listeners to the player
		for (EventListener<? extends Event> listener : playerListeners) {
			player.addEventListener(listener);
		}
		return player;
	}

	public static Npc newNpc() {
		return null;
	}

	public static GameObject newGameObject() {
		return null;
	}

}
