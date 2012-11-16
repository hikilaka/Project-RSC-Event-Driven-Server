package com.projectrsc.gameserver.network;

import java.nio.ByteBuffer;

import org.jboss.netty.buffer.ByteBufferBackedChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public final class PacketEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object message) {
		if (!(message instanceof RSCPacket)) {
			System.out.println("Wrong packet type! " + message.toString());
			return ChannelBuffers.EMPTY_BUFFER;
		}
		RSCPacket p = (RSCPacket) message;
		byte[] data = p.getData();
		int dataLength = data.length;
		ByteBuffer buffer;
		if (!p.isBare()) {
			buffer = ByteBuffer.allocate(dataLength + 3);
			byte[] outlen = {(byte) (dataLength >> 8), (byte) (dataLength)};
			buffer.put(outlen);
			int id = p.getID();
			buffer.put((byte) id);
		} else {
			buffer = ByteBuffer.allocate(dataLength);
		}
		buffer.put(data, 0, dataLength);
		buffer.flip();
		return new ByteBufferBackedChannelBuffer(buffer);
	}

}
