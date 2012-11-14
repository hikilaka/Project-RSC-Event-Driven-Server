package com.projectrsc.gameserver.network;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.projectrsc.gameserver.entity.Player;
import com.projectrsc.gameserver.listeners.ClientMessageListener;
import com.projectrsc.gameserver.model.World;
import com.projectrsc.shared.network.PacketReadException;
import com.projectrsc.shared.network.Session;

/**
 * Handles most of the networking IO
 * 
 * @author Hikilaka
 * @version 1
 * @since 0.1
 */
public final class RSCConnectionHandler extends SimpleChannelHandler {

	private final ClientMessageListener[] messageListeners = new ClientMessageListener[256];

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		Session session = Session.class.cast(ctx.getChannel().getAttachment());
		RSCPacket packet = RSCPacket.class.cast(e.getMessage());
		ClientMessageListener listener = messageListeners[packet.getID()];
		if (listener != null) {
			try {
				listener.onMessageReceived(session, packet);
			} catch (PacketReadException exception) {
				exception.printStackTrace();
			}
		} else {
			System.out.println("Warning null packet listener! " + session + " "
					+ packet);
		}

	}

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {
		Session session = new Session(ctx.getChannel());
		System.out.println("New connection from: " + session);
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) {
		System.out.println("Connection disconnected from: "
				+ ctx.getChannel().getAttachment());
		Session session = Session.class.cast(ctx.getChannel().getAttachment());

		if (session.getAttachment() != null
				&& session.getAttachment() instanceof Player) {
			World.getWorld().unregisterPlayer(
					session.getAttachmentAs(Player.class));
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();
	}

	public void registerMessageListener(ClientMessageListener listener) {
		for (int id : listener.getAssociatedIds()) {
			if (messageListeners[id] != null) {
				throw new RuntimeException(
						"Duplicate message listeners for ID: " + id);
			}
			messageListeners[id] = listener;
		}
	}

}