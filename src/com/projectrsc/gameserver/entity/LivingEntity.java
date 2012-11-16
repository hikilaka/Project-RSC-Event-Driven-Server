package com.projectrsc.gameserver.entity;

import com.projectrsc.gameserver.event.impl.SpriteChangedEvent;
import com.projectrsc.gameserver.event.impl.MovementEvent;
import com.projectrsc.gameserver.model.Location;
import com.projectrsc.gameserver.util.StatefulEntityCollection;

public abstract class LivingEntity extends Entity {

	/**
	 * Possible sprites of this entity
	 */
	private static final int[][] POSSIBLE_SPRITES = new int[][]{{3, 2, 1}, {4, -1, 0}, {5, 6, 7}};

	/**
	 * Whether or not this entity is busy
	 */
	private boolean busy = false;

	/**
	 * Whether or not this entity is registered
	 */
	private boolean registered = false;

	/**
	 * The combat level of this entity
	 */
	private int combatLevel = 3;

	/**
	 * This entity's current sprite
	 */
	private int sprite = 1;

	/**
	 * Used to determine a few things, including timeouts for players, and
	 * movement for npcs
	 */
	private long lastMovement = System.currentTimeMillis();

	/**
	 * A list of entites this entity can see
	 */
	private final StatefulEntityCollection<Player> watchedPlayers = new StatefulEntityCollection<>();

	@Override
	public void setLocation(Location location) {
		setLocation(location, false);
	}
	
	public void setLocation(Location location, boolean teleported) {
		if (!teleported) {
			updateSprite(location);
		}
		resetLastMovement();
		super.setLocation(location);
		fireEvent(new MovementEvent(this));
	}
	
	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	public boolean isRegistered() {
		return registered;
	}

	public void setCombatLevel(int combatLevel) {
		this.combatLevel = combatLevel;
	}

	public int getCombatLevel() {
		return combatLevel;
	}

	public void updateSprite(Location location) {
		int xIndex = getLocation().getX() - location.getX() + 1;
		int yIndex = getLocation().getY() - location.getY() + 1;
		setSprite(POSSIBLE_SPRITES[xIndex][yIndex]);
	}

	public void setSprite(int sprite) {
		this.sprite = sprite;
		fireEvent(new SpriteChangedEvent(this)); //TODO: add listener
	}

	public int getSprite() {
		return sprite;
	}

	public void resetLastMovement() {
		lastMovement = System.currentTimeMillis();
	}

	public long getLastMovement() {
		return lastMovement;
	}

	public StatefulEntityCollection<Player> getWatchedPlayers() {
		return watchedPlayers;
	}

	public boolean informOfPlayer(Player p) {
		if (!watchedPlayers.contains(p) && location.get().withinRange(p.getLocation(), 16, 15)) {
			watchedPlayers.add(p);
			return true;
		}
		return false;
	}

}
