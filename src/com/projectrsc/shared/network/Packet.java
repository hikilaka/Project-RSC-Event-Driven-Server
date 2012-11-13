package com.projectrsc.shared.network;

public class Packet {

	private final Session session;

	private boolean bare;

	private int pLength, caret;

	private byte[] pData;

	public Packet(Session session, byte[] pData) {
		this(session, pData, false);
	}

	public Packet(Session session, byte[] pData, boolean bare) {
		this.session = session;
		this.pData = pData;
		this.pLength = pData.length;
		this.bare = bare;
	}

	public byte[] getData() {
		return pData;
	}

	public int getLength() {
		return pLength;
	}

	public byte[] getRemainingData() {
		byte[] data = new byte[pLength - caret];
		for (int i = 0; i < data.length; i++) {
			data[i] = pData[i + caret];
		}
		caret += data.length;
		return data;
	}

	public Session getSession() {
		return session;
	}

	public boolean isBare() {
		return bare;
	}

	public byte readByte() throws PacketReadException {
		return pData[caret++];
	}
	
	public boolean readBoolean() throws PacketReadException {
		return pData[caret++] == 1;
	}
	
	public short readShort() throws PacketReadException {
		return (short) ((short) ((pData[caret++] & 0xff) << 8) | (short) (pData[caret++] & 0xff));
	}

	public int readInt() throws PacketReadException {
		return ((pData[caret++] & 0xff) << 24) | ((pData[caret++] & 0xff) << 16) |
				((pData[caret++] & 0xff) << 8) | (pData[caret++] & 0xff);
	}

	public long readLong() throws PacketReadException {
		return (long) ((long) (pData[caret++] & 0xff) << 56) | ((long) (pData[caret++] & 0xff) << 48) |
				((long) (pData[caret++] & 0xff) << 40) | ((long) (pData[caret++] & 0xff) << 32) |
				((long) (pData[caret++] & 0xff) << 24) | ((long) (pData[caret++] & 0xff) << 16) |
				((long) (pData[caret++] & 0xff) << 8) | ((long) (pData[caret++] & 0xff));
	}
	
	public byte[] readBytes(int length) throws PacketReadException {
		byte[] data = new byte[length];
		for (int i = 0; i < length; i++) {
			data[i] = pData[i + caret];
		}
		caret += length;
		return data;
	}

	public String readString() throws PacketReadException {
		return readString(pLength - caret);
	}

	public String readString(int length) throws PacketReadException {
		String rv = new String(pData, caret, length);
		caret += length;
		return rv;
	}

	public int remaining() {
		return pData.length - caret;
	}

	public void skip(int x) {
		caret += x;
	}
	
}