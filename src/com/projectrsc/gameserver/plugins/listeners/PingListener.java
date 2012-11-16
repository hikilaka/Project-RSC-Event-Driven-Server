package com.projectrsc.gameserver.plugins.listeners;

import com.projectrsc.gameserver.entity.Player;
import com.projectrsc.gameserver.listeners.ClientMessageListener;
import com.projectrsc.gameserver.network.RSCPacket;
import com.projectrsc.shared.network.PacketReadException;
import com.projectrsc.shared.network.Session;

public final class PingListener extends ClientMessageListener {

	@Override
	public int[] getAssociatedIds() {
		return new int[] { 5 };
	}

	@Override
	public void onMessageReceived(Session session, RSCPacket packet) throws PacketReadException {
		if (session.getAttachment() == null || !(session.getAttachment() instanceof Player)) {
			// Invalid time to send a ping request
			return;
		}
		Player player = session.getAttachmentAs(Player.class);
		player.resetLastPing();
	}

}