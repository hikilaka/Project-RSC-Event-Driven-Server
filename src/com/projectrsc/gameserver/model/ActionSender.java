package com.projectrsc.gameserver.model;

import com.projectrsc.gameserver.entity.Player;
import com.projectrsc.gameserver.network.RSCPacketBuilder;

public final class ActionSender {

	private final Player player;

	public ActionSender(Player player) {
		this.player = player;
	}

	/**
	 * Sends the player their server index, world width, height
	 * and their current height
	 */
	public void sendWorldInformation() {
		RSCPacketBuilder builder = new RSCPacketBuilder().setID(131);
		builder.addShort(player.getIndex());
		builder.addShort(2304).addShort(1776);
		builder.addShort(player.getLocation().getZ());
		builder.addShort(944);
		player.getSession().write(builder.toPacket());
	}

}
