package com.projectrsc.gameserver.network;

import com.projectrsc.shared.network.PacketBuilder;

public final class RSCPacketBuilder extends PacketBuilder {

	private int packetId = 0;

	public RSCPacketBuilder setID(int packetId) {
		this.packetId = packetId;
		return this;
	}

	public RSCPacket toPacket() {
		byte[] data = new byte[curLength];
		System.arraycopy(payload, 0, data, 0, curLength);
		return new RSCPacket(null, packetId, data, bare);
	}
}
