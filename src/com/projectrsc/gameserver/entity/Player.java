package com.projectrsc.gameserver.entity;

import java.util.concurrent.atomic.AtomicLong;

import com.projectrsc.gameserver.model.ActionSender;
import com.projectrsc.gameserver.model.World;
import com.projectrsc.shared.network.Session;

public final class Player extends LivingEntity {

	private final Session session;

	private final String username, password;

	private final AtomicLong lastPing = new AtomicLong(
			System.currentTimeMillis());

	private final ActionSender actionSender = new ActionSender(this);

	protected Player(Session session, String username, String password) {
		this.session = session;
		this.username = username;
		this.password = password;
	}

	public Session getSession() {
		return session;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public long getLastPing() {
		return lastPing.get();
	}

	public void resetLastPing() {
		this.lastPing.set(System.currentTimeMillis());
	}

	public ActionSender getActionSender() {
		return actionSender;
	}

	public void loadSurroundingEntities() {
		for (Player player : World.getWorld().getPlayers()) {
			if (player.getIndex() != index.get()) {
				if (informOfPlayer(player)) { // inform this player of that player
					player.informOfPlayer(this); // inform that player of this  player
					player.getActionSender().sendNewPlayerPosition(this);
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Player(" + username + ", " + session + ")";
	}

}
