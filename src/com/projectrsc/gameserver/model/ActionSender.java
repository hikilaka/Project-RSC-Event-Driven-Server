package com.projectrsc.gameserver.model;

import java.util.Collection;

import com.projectrsc.gameserver.entity.Player;
import com.projectrsc.gameserver.network.RSCPacketBuilder;

public final class ActionSender {

	private final Player player;

	public ActionSender(Player player) {
		this.player = player;
	}

	/**
	 * Sends the player their server index and current height
	 */
	public void sendWorldInformation() {
		RSCPacketBuilder builder = new RSCPacketBuilder().setID(131);
		builder.addShort(player.getIndex());
		builder.addShort(player.getLocation().getZ());
		player.getSession().write(builder.toPacket());
	}

	/**
	 * Notifies the player of surrounding players
	 * upon logging in
	 */
	public void sendInitialPlayerPositions() {
		RSCPacketBuilder builder = new RSCPacketBuilder().setID(42);
		builder.addBits(player.getX(), 11);
		builder.addBits(player.getY(), 13);
		builder.addBits(player.getSprite(), 4);

		Collection<Player> players = player.getWatchedPlayers().getNewEntities();
		builder.addBits(players.size(), 8);
		
		for (Player p : players) {
			int xOffset = p.getLocation().getX() - player.getLocation().getX();
			int yOffset = p.getLocation().getY() - player.getLocation().getY();
			if (xOffset < 0) {
				xOffset += 32;
			}
			if (yOffset < 0) {
				yOffset += 32;
			}
			builder.addBits(p.getIndex(), 16);
			builder.addBits(xOffset, 5);
			builder.addBits(yOffset, 5);
			builder.addBits(p.getSprite(), 4);
			builder.addBits(0, 1);
		}
		player.getSession().write(builder.toPacket());
	}
	
	/**
	 * Sends an update to the client that
	 * they have changed location
	 */
	public void sendPositionUpdate() {
		RSCPacketBuilder builder = new RSCPacketBuilder().setID(43);
		builder.addBits(1, 4);
		builder.addBits(player.getX(), 11);
		builder.addBits(player.getY(), 13);
		builder.addBits(player.getSprite(), 4);
		player.getSession().write(builder.toPacket());
	}
	
	/**
	 * Updates this player of the passed argument's position
	 */
	public void sendPositionUpdate(Player player) {
		RSCPacketBuilder builder = new RSCPacketBuilder().setID(43);
		builder.addBits(2, 4);
		builder.addBits(player.getIndex(), 16);
		builder.addBits(player.getSprite(), 3);
		this.player.getSession().write(builder.toPacket());
	}
	
	/**
	 * Notifies the client of a new player
	 */
	public void sendNewPlayerPosition(Player player) {
		RSCPacketBuilder builder = new RSCPacketBuilder().setID(43);
		builder.addBits(3, 4);
		int xOffset = player.getLocation().getX() - this.player.getLocation().getX();
		int yOffset = player.getLocation().getY() - this.player.getLocation().getY();
		if (xOffset < 0) {
			xOffset += 32;
		}
		if (yOffset < 0) {
			yOffset += 32;
		}
		builder.addBits(player.getIndex(), 16);
		builder.addBits(xOffset, 5);
		builder.addBits(yOffset, 5);
		builder.addBits(player.getSprite(), 4);
		builder.addBits(0, 1);
		this.player.getSession().write(builder.toPacket());
	}
	
	/**
	 * Notifies the client to remove a player
	 */
	public void sendRemovePlayerPosition(Player player) {
		RSCPacketBuilder builder = new RSCPacketBuilder().setID(43);
		builder.addBits(4, 4);
		builder.addBits(player.getIndex(), 16);
		builder.addBits(12, 4); // 12 removes a player
		this.player.getSession().write(builder.toPacket());
	}
	
	/**
	 * Notifies the client of a player sprite change
	 */
	public void sendPlayerSpriteChange(Player player) {
		RSCPacketBuilder builder = new RSCPacketBuilder().setID(43);
		builder.addBits(4, 4);
		builder.addBits(player.getIndex(), 16);
		builder.addBits(player.getSprite(), 4);
		this.player.getSession().write(builder.toPacket());
	}

}
