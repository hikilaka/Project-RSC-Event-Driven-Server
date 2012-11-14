package com.projectrsc.gameserver.plugins.requests;

import com.projectrsc.gameserver.GameServer;
import com.projectrsc.gameserver.entity.EntityFactory;
import com.projectrsc.gameserver.entity.Player;
import com.projectrsc.gameserver.event.detail.OneTimeEvent;
import com.projectrsc.gameserver.event.impl.LoginAcceptedEvent;
import com.projectrsc.gameserver.listeners.ClientMessageListener;
import com.projectrsc.gameserver.model.World;
import com.projectrsc.gameserver.network.RSCPacket;
import com.projectrsc.gameserver.network.RSCPacketBuilder;
import com.projectrsc.gameserver.util.RSADecryptor;
import com.projectrsc.gameserver.util.Settings;
import com.projectrsc.shared.network.Packet;
import com.projectrsc.shared.network.PacketReadException;
import com.projectrsc.shared.network.Session;

public final class LoginRequestListener extends ClientMessageListener {

	private final World world = World.getWorld();
	
	private final RSADecryptor decryptor = new RSADecryptor();

	@Override
	public int[] getAssociatedIds() {
		return new int[] { 77 };
	}

	@Override
	public void onMessageReceived(final Session session, RSCPacket packet) throws PacketReadException {
		if (session.getAttachment() == null || !(session.getAttachment() instanceof Long)) {
			// They are requesting to login without a session request
			// or they're requesting to login after already logging in
			return;
		}
		// The login packet is encrypted with RSA
		byte[] decryptedData = decryptor.decrypt(packet.getData());
		final Packet decryptedPacket = new Packet(session, decryptedData);

		final boolean reconnecting = decryptedPacket.readBoolean();
		final int clientVersion = decryptedPacket.readInt();
		final int[] clientKeys = new int[4];

		for (int i = 0; i < 4; ++i) {
			clientKeys[i] = decryptedPacket.readInt();
		}
		final String username = decryptedPacket.readString(20).trim();
		final String password = decryptedPacket.readString(20).trim();

		// This isn't important enough to use the networking thread pool
		// Let's let the game engine process it
		GameServer.getInstance().getGameEngine().addEvent(new OneTimeEvent() {
			@Override
			public void run() {
				byte responseCode = -1;
				if (clientVersion != Settings.VERSION) {
					responseCode = 4;
				}
				
				//temporary
				responseCode = (byte) (reconnecting ? 1 : 0);
				Player player = EntityFactory.newPlayer(session, username, password);
				session.setAttachment(player);
				world.registerPlayer(player); // must be done before sending world info
				player.getActionSender().sendWorldInformation();
				player.fireEvent(new LoginAcceptedEvent(player));
				//end of temp code
				
				if (responseCode != -1) {
			        RSCPacketBuilder pb = new RSCPacketBuilder();
			        pb.setBare(true).addByte(responseCode);
			        session.write(pb.toPacket());
				}
			}
		});
	}

}
