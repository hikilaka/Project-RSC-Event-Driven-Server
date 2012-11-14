package com.projectrsc.gameserver.listeners;

import com.projectrsc.gameserver.network.RSCPacket;
import com.projectrsc.shared.network.PacketReadException;
import com.projectrsc.shared.network.Session;

public abstract class ClientMessageListener {

	public abstract int[] getAssociatedIds();

	public abstract void onMessageReceived(Session session, RSCPacket packet)
			throws PacketReadException;

}