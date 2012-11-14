package com.projectrsc.gameserver.network;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.projectrsc.shared.network.Session;

public final class PacketDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer msg) {
		try {
			if (msg.readableBytes() >= 3) {
				msg.markReaderIndex();
				byte[] buf = new byte[] { msg.readByte(), msg.readByte() };
				int length = ((short) ((buf[0] & 0xff) << 8) | (short) (buf[1] & 0xff));

				if (length <= msg.readableBytes()) {
					byte[] payload = new byte[length - 1];
					int id = msg.readByte() & 0xff;
					msg.readBytes(payload);
					RSCPacket p = new RSCPacket(Session.class.cast(channel.getAttachment()), id, payload);
					return p;
				} else {
					msg.resetReaderIndex();
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}