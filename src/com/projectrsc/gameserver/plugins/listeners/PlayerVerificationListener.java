package com.projectrsc.gameserver.plugins.listeners;

import com.projectrsc.gameserver.entity.Player;
import com.projectrsc.gameserver.listeners.ClientMessageListener;
import com.projectrsc.gameserver.network.RSCPacket;
import com.projectrsc.shared.network.PacketReadException;
import com.projectrsc.shared.network.Session;

public final class PlayerVerificationListener extends ClientMessageListener {

	@Override
	public int[] getAssociatedIds() {
		return new int[] { 83 };
	}

	@Override
	public void onMessageReceived(Session session, RSCPacket packet) throws PacketReadException {
		if (!(session.getAttachment() instanceof Player)) {
			return;
		}
		final Player player = session.getAttachmentAs(Player.class);
		final int readPlayerCount = packet.readShort();
		final int[] readPlayerIndicies = new int[readPlayerCount];
		final int[] readPlayerAppearanceIds = new int[readPlayerCount];
		
		for (int i = 0; i < readPlayerCount; ++i) {
			readPlayerIndicies[i] = packet.readShort();
			readPlayerAppearanceIds[i] = packet.readShort();
		}	
		//TODO: what are these used for??
		player.getWatchedPlayers().update();
	}

}
