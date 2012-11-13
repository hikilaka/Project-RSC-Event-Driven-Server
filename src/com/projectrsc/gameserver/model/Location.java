package com.projectrsc.gameserver.model;

import com.projectrsc.shared.util.Pair;

/**
 * Should this be a Pair<Integer, Integer>?
 * 
 * @author Hikilaka
 * 
 */
public final class Location {

	private final int x, y;

	public Location(Pair<Integer, Integer> pair) {
		this.x = pair.getFirst();
		this.y = pair.getSecond();
	}

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getZ() {
		return (y / 944);
	}

	public int getWildernessLevel() {
		int wildY = 2203 - (y + (1776 - (944 * getHeight(y))));
		if (x + 2304 >= 2640) {
			wildY = -50;
		}
		if (wildY > 0) {
			return 1 + wildY / 6;
		}
		return 0;
	}

	public boolean withinRange(Location other, int radius) {
		int xDifference = Math.abs(x - other.x);
		int yDifference = Math.abs(y - other.y);
		return xDifference <= radius && xDifference > -radius && yDifference <= radius && yDifference > -radius;
	}
	
	public boolean withinRange(Location other, int top, int bottom) {
		int xDifference = Math.abs(x - other.x);
		int yDifference = Math.abs(y - other.y);
		return xDifference <= top && xDifference > -bottom && yDifference <= top && yDifference > -bottom;
	}
	
	public boolean inWilderness() {
		return getWildernessLevel() > 0;
	}

	@Override
	public int hashCode() {
		return (x << 32) | y;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
		if (object == null || !(object instanceof Location))
			return false;
		Location other = Location.class.cast(object);
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "Location(" + x + ", " + y + ")";
	}

	public static int getHeight(Location loc) {
		return (int) (loc.y / 944);
	}

	public static int getHeight(int y) {
		return (int) (y / 944);
	}

}
