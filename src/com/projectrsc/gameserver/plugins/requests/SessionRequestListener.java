package com.projectrsc.gameserver.plugins.requests;

import java.security.SecureRandom;

import com.projectrsc.gameserver.GameServer;
import com.projectrsc.gameserver.event.detail.OneTimeEvent;
import com.projectrsc.gameserver.listeners.ClientMessageListener;
import com.projectrsc.gameserver.network.RSCPacket;
import com.projectrsc.gameserver.network.RSCPacketBuilder;
import com.projectrsc.shared.network.PacketReadException;
import com.projectrsc.shared.network.Session;

public final class SessionRequestListener extends ClientMessageListener {

	private final SecureRandom random = new SecureRandom();

	@Override
	public int[] getAssociatedIds() {
		return new int[] { 32 };
	}

	@Override
	public void onMessageReceived(final Session session, RSCPacket packet) throws PacketReadException {
		if (session.getAttachment() != null) {
			// Already has an initialized session
			return;
		}
		byte usernameKey = packet.readByte();
		final Long serverKey = random.nextLong() >> usernameKey;

		GameServer.getInstance().getGameEngine().addEvent(new OneTimeEvent() {
			@Override
			public void run() {
				session.setAttachment(serverKey);
				RSCPacketBuilder pb = new RSCPacketBuilder();
				pb.setBare(true).addLong(serverKey);
				session.write(pb.toPacket());
			}
		});
	}

}
