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
		RSCPacketBuilder packet = new RSCPacketBuilder().setID(42);
		packet.addBits(player.getX(), 11);
		packet.addBits(player.getY(), 13);
		packet.addBits(player.getSprite(), 4);

		Collection<Player> players = player.getWatchedPlayers().getNewEntities();
		packet.addBits(players.size(), 8);
		
		for (Player p : players) {
			int xOffset = p.getLocation().getX() - player.getLocation().getX();
			int yOffset = p.getLocation().getY() - player.getLocation().getY();
			if (xOffset < 0) {
				xOffset += 32;
			}
			if (yOffset < 0) {
				yOffset += 32;
			}
			packet.addBits(p.getIndex(), 16);
			packet.addBits(xOffset, 5);
			packet.addBits(yOffset, 5);
			packet.addBits(p.getSprite(), 4);
			packet.addBits(0, 1);
		}
		player.getSession().write(packet.toPacket());
	}

}
