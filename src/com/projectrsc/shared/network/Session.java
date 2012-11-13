package com.projectrsc.shared.network;

import java.util.concurrent.atomic.AtomicReference;

import org.jboss.netty.channel.Channel;

/**
 * This class act's as a wrapper for a <code>Channel</code>
 * in the Netty API
 * 
 * @author Hikilaka
 * @version 1
 * @since 0.1
 */
public final class Session {
	
	private final Channel channel;
	
	private final AtomicReference<Object> attachment = new AtomicReference<>();
	
	/**
	 * @param channel The channel to 'wrap'
	 */
	public Session(Channel channel) {
		this.channel = channel;
		channel.setAttachment(this);
	}
	
	/**
	 * Writes an object to the channel
	 * @param object The object to write
	 */
	public void write(Object object) {
		channel.write(object);
	}
	
	/**
	 * Closes this session
	 */
	public void close() {
		channel.close();
	}
	
	/**
	 * Sets the <code>attachment<code>
	 * @param attachment The new attachment
	 */
	public void setAttachment(Object attachment) {
		this.attachment.set(attachment);
	}
	
	/**
	 * Gets the attachment
	 * @return The attachment as a <code>Object</code>
	 */
	public Object getAttachment() {
		return attachment.get();
	}
	
	/**
	 * Gets the attachment as the specified class type
	 * @param type The <code>Class<code> type to interpret the object as
	 * @return
	 */
	public <T> T getAttachmentAs(Class<T> type) {
		return type.cast(attachment.get());
	}
	
	@Override
	public String toString() {
		return "Session(" + channel.getRemoteAddress().toString() + ")";
	}

}