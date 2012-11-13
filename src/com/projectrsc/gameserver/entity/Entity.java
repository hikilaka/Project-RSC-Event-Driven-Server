package com.projectrsc.gameserver.entity;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.projectrsc.gameserver.model.Location;

public abstract class Entity {
	
	private static final Location DEFAULT_LOCATION = new Location(500, 300);
	
	/**
	 * This entity's server index
	 */
	protected final AtomicInteger index = new AtomicInteger(0);
	
	/**
	 * This entity's location
	 */
	protected final AtomicReference<Location> location = new AtomicReference<>(DEFAULT_LOCATION);
	
	/**
	 * This entity's direction
	 */
	protected final AtomicInteger direction = new AtomicInteger(0);
	
	/**
	 * Invoked when this entity attacks another
	 * @param target The entity that this entity attacked
	 */
	public abstract void onMelee(Entity target);

	/**
	 * Invoked when this entity ranges another entity
	 * @param target The entity this entity has ranged
	 */
	public abstract void onRange(Entity target);

	/**
	 * Invoked when this entity casts a spell on another entity
	 * @param target The entity the spell is casted on
	 */
	public abstract void onMage(Entity target/*, SpellDescriptor */);
	
	public void setIndex(int index) {
		this.index.set(index);
	}
	
	public int getIndex() {
		return index.get();
	}
	
	public void setLocation(Location location) {
		this.location.set(location);
	}
	
	public Location getLocation() {
		return location.get();
	}
	
	public int getX() {
		return location.get().getX();
	}
	
	public int getY() {
		return location.get().getY();
	}
	
	public int getDirection() {
		return direction.get();
	}
	
	public void setDirection(int direction) {
		this.direction.set(direction);
	}
	
}