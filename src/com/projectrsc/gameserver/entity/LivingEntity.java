package com.projectrsc.gameserver.entity;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.projectrsc.gameserver.util.StatefulEntityCollection;

public abstract class LivingEntity extends Entity {
	
	/**
	 * Whether or not this entity is busy
	 */
	private final AtomicBoolean busy = new AtomicBoolean(false);
	
	/**
	 * Whether or not this entity is valid
	 */
	private final AtomicBoolean valid = new AtomicBoolean(false);
	
	/**
	 * The combat level of this entity
	 */
	private final AtomicInteger combatLevel = new AtomicInteger(1);
	
	/**
	 * Used to determine a few things, including timeouts for players,
	 * and movement for npcs
	 */
	private final AtomicLong lastMovement = new AtomicLong(System.currentTimeMillis());
	
	/**
	 * A list of entites this entity can see
	 */
	private final StatefulEntityCollection<Player> watchedPlayers = new StatefulEntityCollection<>();
	
	public void setBusy(boolean busy) {
		this.busy.set(busy);
	}
	
	public boolean isBusy() {
		return busy.get();
	}
	
	public void setValid(boolean valid) {
		this.valid.set(valid);
	}
	
	public boolean isValid() {
		return valid.get();
	}
	
	public void setCombatLevel(int combatLevel) {
		this.combatLevel.set(combatLevel);
	}
	
	public int getCombatLevel() {
		return combatLevel.get();
	}
	
	public void resetLastMovement() {
		lastMovement.set(System.currentTimeMillis());
	}
	
	public long getLastMovement() {
		return lastMovement.get();
	}
	
	public StatefulEntityCollection<Player> getWatchedPlayers() {
		return watchedPlayers;
	}
	
	public void informOfPlayer(Player p) {
		if ((!watchedPlayers.contains(p) || watchedPlayers.isRemoving(p)) && location.get().withinRange(p.getLocation(), 16, 15)) {
			watchedPlayers.add(p);
		}
	}

}
