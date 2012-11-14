package com.projectrsc.gameserver.model;

import com.projectrsc.gameserver.entity.Player;
import com.projectrsc.gameserver.util.EntityList;

public final class World {

	private static final World INSTANCE = new World();

	public static World getWorld() {
		return INSTANCE;
	}

	private final EntityList<Player> players = new EntityList<>();
	
	public void registerPlayer(Player player) {
		player.setRegistered(true);
		player.loadSurroundingEntities();
		players.add(player);
	}

	public void unregisterPlayer(Player player) {
		players.remove(player);
	}

	public EntityList<Player> getPlayers() {
		return players;
	}

	public Player getPlayer(int index) {
		return players.get(index);
	}

}
