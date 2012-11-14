package com.projectrsc.gameserver.network;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public final class RSCPipelineFactory implements ChannelPipelineFactory {

	private final PacketDecoder decoder = new PacketDecoder();

	private final PacketEncoder encoder = new PacketEncoder();

	private final RSCConnectionHandler handler;

	public RSCPipelineFactory(RSCConnectionHandler handler) {
		this.handler = handler;
	}

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("decoder", decoder);
		pipeline.addLast("encoder", encoder);
		pipeline.addLast("handler", handler);
		return pipeline;
	}

}
